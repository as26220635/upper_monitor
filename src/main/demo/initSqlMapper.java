import cn.kim.util.FuncUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class initSqlMapper {

    public static void main(String[] args) throws IOException {
        //字段
        String str = "ID\tBD_PARENT_ID\tBD_NAME\tBD_CONTACTS\tBD_PHONE\tBD_FIXED_PHONE\tBD_EMAIL\tBD_ADDRESS\tBD_DESCRIBE\tBD_ENTER_TIME";
        //表名
        String tablename = "BUS_DIVISION";
        creatsql(str, tablename);
    }

    public static void creatsql(String str, String tablename) {
        String[] array1 = tablename.split("_");
        String[] array2 = new String[array1.length - 1];

        FuncUtil.forEach(Arrays.asList(array1), (index, s) -> {
            if (index != 0) {
                array2[index - 1] = s;
            }
        });
        String tablename2 = Arrays.stream(array2).map(i -> i.substring(0, 1).toUpperCase() + i.substring(1, i.length()).toLowerCase()).collect(Collectors.joining());
        //首位大写
//        tablename2 = tablename2.substring(0, 1).toUpperCase() + tablename2.substring(1, tablename2.length());
        str = str.replace(" ", "").replaceAll("\t", ",");
        String[] strr = str.split(",");
        System.out.println("字段数量：" + strr.length + "\n");
        String str2 = "";
        String select = "\t<!--查询-->\n\t<select id=\"select" + tablename2 + "\" parameterType=\"java.util.Map\" resultType=\"java.util.Map\">";
        String str3 = "\t<!--插入-->\n\t<insert id=\"insert" + tablename2 + "\" parameterType=\"java.util.Map\">" +
                "\n\t\tINSERT INTO " + tablename
                + "\n\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n";
        String str4 = "";
        String str5 = "\t<!--编辑-->\n\t<update id=\"update" + tablename2 + "\" parameterType=\"java.util.Map\">\n\t\tUPDATE " + tablename + "\n\t\t<set>\n";
        String str6 = "Map<String, Object> paramMap = new HashMap<>();\n";
        String delete = "\t<!--删除-->\n\t<delete id=\"delete" + tablename2 + "\" parameterType=\"java.util.Map\">";

        System.out.println("Mapper:");
        //查询
        select += "\n\t\tSELECT * FROM " + tablename + " \n\t\t<where>";
        select += "\n\t\t\t<if test =\"ID != NULL and ID != ''\">\n\t\t\t\tAND ID = #{ID}\n\t\t\t</if>";
        select += "\n\t\t</where>\n\t</select>";

        System.out.println(select + "\n");

        for (String string : strr) {
            str3 += "\t\t\t<if test=\"" + string + " != null\">" + string + ",</if>\n";
        }
        str3 += "\t\t</trim>\n\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n";

        for (String string : strr) {
            str3 += "\t\t\t<if test=\"" + string + " != null\">#{" + string + "},</if>\n";
        }
        str3 += "\t\t</trim>\n\t</insert>";
        System.out.println(str3 + "\n");

        for (String string : strr) {
            str5 += "\t\t\t<if test=\"" + string + " != null\">" + string + "=#{" + string + "},</if>\n";
        }
        str5 += "\t\t</set>\n\t\t<where>\n\t\t\t<if test=\"ID !=null\">ID=#{ID}</if>\n\t\t</where>\n\t</update>";
        System.out.println(str5 + "\n");

        //删除
        delete += "\n\t\tDELETE FROM " + tablename + " WHERE ID = #{ID}";
        delete += "\n\t</delete>";
        System.out.println(delete);

        for (String string : strr) {
            str2 += "param.put(\"" + string + "\",request.getParameter(\"" + string + "\"));\n";
        }
        for (String string : strr) {
            str4 += "String " + string + "=request.getParameter(\"" + string + "\"));\n";
        }

        System.out.println("Controller:");
        System.out.println(str4);
        System.out.println(str2);
        System.err.println(strr.length);

        for (String string : strr) {
            str6 += "paramMap.put(\"" + string + "\", mapParam.get(\"" + string + "\"));\n";
        }
        System.out.println("ServiceImpl:");
        System.out.println(str6);
        System.err.println(strr.length);
    }

}
