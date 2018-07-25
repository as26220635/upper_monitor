package cn.kim.dao;

import cn.kim.common.attr.ConfigProperties;
import cn.kim.common.attr.ConfigProperties;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

public class MapperRefresh implements Runnable {

    private static Logger log = LogManager.getLogger(MapperRefresh.class.getName());

    private static String filename = "mybatis-refresh.properties";
    private static Properties prop = new Properties();
    /**
     * 是否启用Mapper刷新线程功能
     */
    private static boolean enabled;
    /**
     * 刷新启用后，是否启动了刷新线程
     */
    private static boolean refresh;
    /**
     * Mapper实际资源路径
     */
    private Set<String> location;
    /**
     * Mapper资源路径
     */
    private Resource[] mapperLocations;
    /**
     * MyBatis配置对象
     */
    private Configuration configuration;
    /**
     * 上一次刷新时间
     */
    private Long beforeTime = 0L;
    /**
     * 延迟刷新秒数
     */
    private static int delaySeconds;
    /**
     * 休眠时间
     */
    private static int sleepSeconds;
    /**
     * xml文件夹匹配字符串，需要根据需要修改
     */
    private static String mappingPath;

    static {
        try {
            prop.load(new FileInputStream(ConfigProperties.ROOT_PATH + "/" + filename));
        } catch (Exception e) {
            log.error("Load mybatis-refresh [" + filename + "] file error : " + e.getMessage());
        }
        enabled = "true".equalsIgnoreCase(getPropString("enabled"));
        delaySeconds = getPropInt("delaySeconds");
        sleepSeconds = getPropInt("sleepSeconds");
        mappingPath = getPropString("mappingPath");
        delaySeconds = delaySeconds == 0 ? 50 : delaySeconds;
        sleepSeconds = sleepSeconds == 0 ? 3 : sleepSeconds;
        mappingPath = StringUtils.isBlank(mappingPath) ? "mappings" : mappingPath;
    }

    public static boolean isRefresh() {
        return refresh;
    }

    public MapperRefresh(Resource[] mapperLocations, Configuration configuration) {
        this.mapperLocations = mapperLocations;
        this.configuration = configuration;
    }

    @Override
    public void run() {
        beforeTime = System.currentTimeMillis();
        if (enabled) {
            // 启动刷新线程
            final MapperRefresh runnable = this;

            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("MyBatis-Mapper-Refresh").build();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L,
                    TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            threadPoolExecutor.execute(() -> {
                if (location == null) {
                    location = Sets.newHashSet();
                    log.debug("MapperLocation's length:" + mapperLocations.length);
                    for (Resource mapperLocation : mapperLocations) {
                        String s = mapperLocation.toString().replaceAll("\\\\", "/");
                        s = s.substring("file [".length(), s.lastIndexOf(mappingPath) + mappingPath.length());
                        if (!location.contains(s)) {
                            location.add(s);
                            log.debug("Location:" + s);
                        }
                    }
                    log.debug("Locarion's size:" + location.size());
                }
                try {
                    Thread.sleep(delaySeconds * 1000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                refresh = true;
                while (true) {
                    try {
                        for (String s : location) {
                            runnable.refresh(s, beforeTime);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    try {
                        Thread.sleep(sleepSeconds * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 执行刷新
     *
     * @param filePath   刷新目录
     * @param beforeTime 上次刷新时间
     * @throws NestedIOException     解析异常
     * @throws FileNotFoundException 文件未找到
     * @author ThinkGem
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void refresh(String filePath, Long beforeTime) throws Exception {
        // 获取需要刷新的Mapper文件列表
        List<File> fileList = this.getRefreshFile(new File(filePath), beforeTime);
        // 如果刷新了文件，则修改刷新时间，否则不修改
        for (int i = 0; i < fileList.size(); i++) {
            InputStream inputStream = new FileInputStream(fileList.get(i));
            String resource = fileList.get(i).getAbsolutePath();
            try {
                // 清理原有资源，更新为自己的StrictMap方便，增量重新加载
                String[] mapFieldNames = new String[]{"mappedStatements", "caches", "resultMaps", "parameterMaps", "keyGenerators", "sqlFragments"};
                for (String fieldName : mapFieldNames) {
                    Field field = configuration.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Map map = ((Map) field.get(configuration));
                    if (!(map instanceof StrictMap)) {
                        Map newMap = new StrictMap(StringUtils.capitalize(fieldName) + "collection");
                        for (Object key : map.keySet()) {
                            try {
                                newMap.put(key, map.get(key));
                            } catch (IllegalArgumentException ex) {
                                newMap.put(key, ex.getMessage());
                            }
                        }
                        field.set(configuration, newMap);
                    }
                }

                // 清理已加载的资源标识，方便让它重新加载。
                Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
                loadedResourcesField.setAccessible(true);
                Set loadedResourcesSet = ((Set) loadedResourcesField.get(configuration));
                loadedResourcesSet.remove(resource);

                // 重新编译加载资源文件。
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            } catch (Exception e) {
                throw new NestedIOException("Failed to parse mapping resource: '" + resource + "'", e);
            } finally {
                ErrorContext.instance().reset();
            }
        }
        // 如果刷新了文件，则修改刷新时间，否则不修改
        if (fileList.size() > 0) {
            this.beforeTime = System.currentTimeMillis();
            for (int i = 0; i < fileList.size(); i++) {
                log.info("Refresh mybatis file: " + mappingPath + "/" + fileList.get(i).getName());
            }
        }
    }

    /**
     * 获取需要刷新的文件列表
     *
     * @param dir        目录
     * @param beforeTime 上次刷新时间
     * @return 刷新文件列表
     */
    private List<File> getRefreshFile(File dir, Long beforeTime) {
        List<File> fileList = new ArrayList<File>();

        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    fileList.addAll(this.getRefreshFile(file, beforeTime));
                } else if (file.isFile()) {
                    if (this.checkFile(file, beforeTime)) {
                        fileList.add(file);
                    }
                } else {
                    log.error("Refresh mybatis Error file:" + file.getName());
                }
            }
        }
        return fileList;
    }

    /**
     * 判断文件是否需要刷新
     *
     * @param file       文件
     * @param beforeTime 上次刷新时间
     * @return 需要刷新返回true，否则返回false
     */
    private boolean checkFile(File file, Long beforeTime) {
        if (file.lastModified() > beforeTime) {
            return true;
        }
        return false;
    }

    /**
     * 获取整数属性
     *
     * @param key
     * @return
     */
    private static int getPropInt(String key) {
        int i = 0;
        try {
            i = Integer.parseInt(getPropString(key));
        } catch (Exception e) {
        }
        return i;
    }

    /**
     * 获取字符串属性
     *
     * @param key
     * @return
     */
    private static String getPropString(String key) {
        return prop == null ? null : prop.getProperty(key);
    }

    /**
     * 重写 org.apache.ibatis.session.Configuration.StrictMap 类 来自
     * MyBatis3.4.0版本，修改 put 方法，允许反复 put更新。
     */
    public static class StrictMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -4950446264854981133L;
        private String name;

        public StrictMap(String name, int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
            this.name = name;
        }

        public StrictMap(String name, int initialCapacity) {
            super(initialCapacity);
            this.name = name;
        }

        public StrictMap(String name) {
            super();
            this.name = name;
        }

        public StrictMap(String name, Map<String, ? extends V> m) {
            super(m);
            this.name = name;
        }

        @SuppressWarnings("unchecked")
        @Override
        public V put(String key, V value) {
            // ThinkGem 如果现在状态为刷新，则刷新(先删除后添加)
            if (MapperRefresh.isRefresh()) {
                remove(key);
                MapperRefresh.log.debug("refresh key:" + key.substring(key.lastIndexOf(".") + 1));
            }
            // ThinkGem end
            if (containsKey(key)) {
                throw new IllegalArgumentException(name + " already contains value for " + key);
            }
            if (key.contains(".")) {
                final String shortKey = getShortName(key);
                if (super.get(shortKey) == null) {
                    super.put(shortKey, value);
                } else {
                    super.put(shortKey, (V) new Ambiguity(shortKey));
                }
            }
            return super.put(key, value);
        }

        @Override
        public V get(Object key) {
            V value = super.get(key);
            if (value == null) {
                throw new IllegalArgumentException(name + " does not contain value for " + key);
            }
            if (value instanceof Ambiguity) {
                throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name + " (try using the full name including the namespace, or rename one of the entries)");
            }
            return value;
        }

        private String getShortName(String key) {
            final String[] keyparts = key.split("\\.");
            return keyparts[keyparts.length - 1];
        }

        protected static class Ambiguity {
            private String subject;

            public Ambiguity(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }
        }
    }

}
