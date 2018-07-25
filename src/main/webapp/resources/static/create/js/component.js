/**
 * Created by 余庚鑫 on 2017/8/20.
 * 通用组件
 */
//全局缓存
var overallCache = {};
//颜色
var cacheColorGroup = {};
var cacheColorIndex = 0;

function getColor() {
    var color = ['btn-primary', 'btn-success', 'btn-info', 'btn-danger', 'btn-warning', 'btn-default'];
    if (cacheColorIndex == color.length) {
        cacheColorIndex = 0;
    }
    return color[cacheColorIndex++];
}

/**
 *根据顺序获取
 */
function getOrderColor(name) {
    var color = '';
    if (isEmpty(cacheColorGroup[name])) {
        color = getColor();
        cacheColorGroup[name] = color;
    } else {
        color = cacheColorGroup[name];
    }
    return color;
}

/**
 * 清空顺序缓存
 */
function clearOrderColor() {
    cacheColorGroup = {};
    cacheColorIndex = 0;
}

/**
 * 初始化组件
 * @type {{sidebarMenu: mainInit.sidebarMenu, initCarousel: mainInit.initCarousel, initHead: mainInit.initHead, initPjax: mainInit.initPjax, initTips: mainInit.initTips, initPaging: mainInit.initPaging, initAnimated: mainInit.initAnimated, initMenuAnimated: mainInit.initMenuAnimated, initAffix: mainInit.initAffix, initImgWidth: mainInit.initImgWidth, initDotdotdot: mainInit.initDotdotdot, initPerfectScrollbar: mainInit.initPerfectScrollbar, initImagesLoaded: mainInit.initImagesLoaded, toppos: number, scrollHead: Function}}
 */
mainInit = {
    // 后台菜单
    sidebarMenu: function (menu) {
        //动画速度
        var animationSpeed = 200;

        $(menu).on('click', 'li a', function (e) {
            var $this = $(this);
            var checkElement = $this.next();

            if (checkElement.is('.treeview-menu') && checkElement.is(':visible')) {
                checkElement.slideUp(animationSpeed, function () {
                    $this.removeClass("active");
                    checkElement.removeClass('menu-open');
                });
                checkElement.parent('li').removeClass('active');
            }

            //If the menu is not visible
            else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
                //Get the parent menu
                var parent = $this.parents('ul').first();
                //Close all open menus within the parent
                var ul = parent.find('ul:visible').slideUp(animationSpeed);
                //Remove the menu-open class from the parent
                ul.removeClass('menu-open');
                //Get the parent li
                var parent_li = $this.parent("li");
                $(menu).find('li a').removeClass('active');
                //Open the target menu and add the menu-open class
                checkElement.slideDown(animationSpeed, function () {
                    //Add the class active to the parent li
                    checkElement.addClass('menu-open');
                    $this.addClass('active');
                    // parent.find('li.active').removeClass('active');
                    parent_li.addClass('active');
                });
            }
            //if this isn't a link, prevent the page from being redirected
            if (checkElement.is('.treeview-menu')) {
                e.preventDefault();
            }
        });
    },
    //轮播器滚动
    initCarousel: function (carousel, value) {

        $(carousel).owlCarousel({
            items: 1,
            loop: true,
            autoplay: true,
            autoplayTimeout: value,
            autoplayHoverPause: true,
        });

        mainInit.initCarouselJqthumb(carousel + ' .item img');

    },
    initCarouselJqthumb: function (jquery) {
        $(jquery).jqthumb({
            classname: 'jqthumb',
            width: '100%',
            height: '400px',
            position: {y: '50%', x: '50%'},
            zoom: '1',
            method: 'auto',
            after: function (imgObj) {
                $(imgObj).siblings('.head-loading').remove();
            },
            done: function () {
                $(jquery).remove();
            }
        });
    },
    //启用标题动画
    initHead: function (options) {
        var settings = $.extend({
            title: "标题",
        }, options);

        $("#navbarHeaderTitle").html(settings.title);

        $(".navbar-header-title a").click(function () {
            $('body,html').stop().animate({scrollTop: 0}, 250);
        })
        mainInit.toppos = 0;
        $(window).on('scroll', mainInit.scrollHead);
    },
    //启用pjax
    initPjax: function () {
        //加载条
        $(document).on('pjax:start', function () {
            NProgress.start();
        });
        $(document).on('pjax:end', function () {
            NProgress.done();
        });
        $(document).on('pjax:timeout', function (event) {
            event.preventDefault();
        })
        $('a[data-pjax]').pjax();
    },
    //设置内容提示
    initTips: function (options) {
        var settings = $.extend({
            context: "#ontent",
            icon: "mdi-information-outline",
            content: "没有找到对应内容"
        }, options);
        $(settings.context).html('<div class="row tips-content"> <div class="text-light-blue text-center col-md-12"> <div class="text-center"> <i class="text-info mdi ' + settings.icon + '" style="font-size:80px;"></i></div><h1>' + settings.content + '</h1> <div class="btn-group"> <button class="btn btn-info col-md-6" onclick="backHtml()">上一页</button> <button class="btn btn-primary col-md-6" onclick="indexHtml()">主页</button> </div></div> </div>');
    },
    //设置分页条
    initPaging: function (options) {
        var settings = $.extend({
            paging: "#pagination",
            currentPage: 1,
            totalPages: 0,
            visiblePages: 7
        }, options);

        $.jqPaginator(settings.paging, {
            totalPages: settings.totalPages,
            visiblePages: settings.visiblePages,
            currentPage: settings.currentPage,
            first: '<li class="first"><a href="javascript:void(0)">首页</a></li>',
            prev: '<li class="prev"><a href="javascript:void(0)"><i ></i>上一页</a></li>',
            next: '<li class="next"><a href="javascript:void(0)">下一页<i ></i></a></li>',
            last: '<li class="last"><a href="javascript:void(0)">末页</a></li>',
            page: '<li class="page"><a href="javascript:void(0)">{{page}}</a></li>',
            onPageChange: function (num, type) {
                if (type == "change") {
                    loadData(num, type);
                }
            }
        });

    },
    //列表动画,默认滚动出现到屏幕的时候触发
    initAnimated: function (options) {
        var settings = $.extend({
            jquery: ".article",
            animated: "fadeInUp",
            offset: 'bottom-in-view',
        }, options);

        // $(settings.jquery).css("opacity", "0");
        //滚动到就触发动画动画
        // setTimeout(function () {
        //     waypoints();
        // }, 150, 'easeInOutExpo');
        var waypoints = function () {
            $(settings.jquery).waypoint({
                handler: function (direction) {
                    if (direction === 'down' && !$(this).hasClass(settings.animated)) {
                        var el = $(this.element);
                        // el.animate({opacity: 1}, 200);
                        el.addClass(settings.animated + ' animated');
                    }
                },
                offset: settings.offset
            });
        };
        waypoints();
    },
    //菜单动画
    initMenuAnimated: function (options) {
        var settings = $.extend({
            jquery: ".article",
            animated: "fadeInUp",
            scrollTop: 200,
        }, options);

        //滚动到一定的位置
        el = $(settings.jquery);
        $(window).scroll(debounce(function () {
            var scrollTop = $(window).scrollTop();
            if (scrollTop > settings.scrollTop) {
                el.animate({opacity: 1}, 200);
            } else {
                el.animate({opacity: 0}, 200);
            }
        }, 70));
    },
    //悬浮栏
    initAffix: function (options) {
        var settings = $.extend({
            jquery: "#myAffix",
            top: 250
        }, options);

        $(settings.jquery).affix({
            offset: {
                top: settings.top
            }
        });
    },
    //图片宽度超过屏幕了就限制
    initImgWidth: function (options) {
        var settings = $.extend({
            jquery: ".col-md-7.col-md-offset-1 img",
        }, options);

        var imgWidth = $(settings.jquery).parent().width();
        $(settings.jquery).each(function () {
            if ($(this).width() >= imgWidth) {
                $(this).css("width", "100%").css("height", "auto");
            }
        });
    },
    //超过多少字显示省略号
    initDotdotdot: function (options) {
        var settings = $.extend({
            jquery: ".article-title",
            maxLength: 45
        }, options);

        $(settings.jquery).dotdotdot({maxLength: settings.maxLength});
    },
    initPerfectScrollbar: function (options) {
        var settings = $.extend({
            id: ".article-title",
            options: {}
        }, options);
        var ps = new PerfectScrollbar(document.getElementById(settings.id), settings.options);
        return ps;
    },
    initImagesLoaded: function (options) {
        var settings = $.extend({
            jquery: CONTAINER,
            src: BASE_URL + IMG_ERROR
        }, options);

        //判断图片是否被正确加载，没有加载显示默认图片
        $(settings.jquery).imagesLoaded().progress(function (instance, image) {
            if (image.isLoaded) {
                $(image.img).one('error', function () {
                    $(this).prop('src', settings.src);
                    if ($(this).prop('id') == 'carouselImg') {
                        var $parent = $(this).parent();
                        $parent.next().remove();
                        $parent.html('<div class="jqthumb" style="width: 100%; height: 400px; position: relative; overflow: hidden;"><div style="width: 100%; height: 100%; background-image: url(&#39;' + BASE_URL + 'reception/assets/img/error.jpg&#39;); background-repeat: no-repeat; background-position: 50% 50%; background-size: cover; position: absolute; left: 0%;"></div></div>');
                    }
                });
            } else {
                $(image.img).prop('src', settings.src);
                if ($(image.img).prop('id') == 'carouselImg') {
                    var $parent = $(image.img).parent();
                    $parent.next().remove();
                    $parent.html('<div class="jqthumb" style="width: 100%; height: 400px; position: relative; overflow: hidden;"><div style="width: 100%; height: 100%; background-image: url(&#39;' + BASE_URL + 'reception/assets/img/error.jpg&#39;); background-repeat: no-repeat; background-position: 50% 50%; background-size: cover; position: absolute; left: 0%;"></div></div>');
                }
            }
        });
    },
    toppos: 0,
    scrollHead: debounce(function () {
        //标题动画切换
        var scrollTop = $(document).scrollTop();
        if (scrollTop > 260) {
            if (scrollTop > mainInit.toppos) {
                $('#navbarHeader1').addClass("header-out");
                $('#navbarHeader2').addClass("header-up");
            } else {
                $('#navbarHeader1').removeClass("header-out");
                $('#navbarHeader2').removeClass("header-up");
            }
        }
        mainInit.toppos = scrollTop;
    }, 50),
}
/**
 * 加载动画
 * @type {{show: loading.show, hide: loading.hide, init: loading.init, spin: loading.spin, remove: loading.remove}}
 */
loading = {
    show: function (options) {
        var settings = $.extend({
            id: undefined,
            tips: '加载中'
        }, options);
        if (settings.id != undefined) {
            if ($('#loading' + settings.id).length > 0) {
                return;
            }
        }
        var html = "<div class='z-index-main fixed-center loading-fixed animated bounceIn' id='loading" + settings.id + "'>" +
            "<div class='loading-main'><i class='mdi mdi-24px mdi-spin mdi-loading text-center'></i>" + settings.tips + "</div></div>";
        $("body").append(html);
    },
    hide: function () {
        $('.loading-fixed').remove();
    },
    init: function (jquery) {
        var url = BASE_URL + IMG_LODING;
        var loadingHtml = "<img src='" + url + "' style='width: 100%;height: auto' class='img-rounded img-responsive loading-img'/>"
        $(jquery).html(loadingHtml);
    },
    spin: function (jquery) {
        var loadingHtml = '<div class="html-loading"><i class="mdi mdi-48px mdi-spin mdi-loading"></i></div>';
        $(jquery).html(loadingHtml);
    },
    remove: function (jquery) {
        $(jquery).children(".loading-img").remove();
    }
}

/**
 * table表单
 * @type {{isSerach: boolean, saveCacheValue: tableView.saveCacheValue, callbackCacheValue: tableView.callbackCacheValue, clearCacheValue: tableView.clearCacheValue, resetSerach: tableView.resetSerach, reload: tableView.reload, rowData: function(*, *=): *, addClass: tableView.addClass, visible: tableView.visible, btnClick: Function, orderNumber: tableView.orderNumber, choiceBox: tableView.choiceBox, init: function(*=): *}}
 */
tableView = {
    //是否搜索模式
    isSerach: false,
    //记录缓存的值
    saveCacheValue: function (key, queryForm) {
        var cache = {};
        if (!isEmpty(queryForm)) {
            //获取select的值
            queryForm.find('select').each(function () {
                var $select = $(this);
                cache[$select.prop('name')] = $select.val();
            });
            //获取input的值
            queryForm.find('input').each(function () {
                var $input = $(this);
                cache[$input.prop('name')] = $input.val();
            });
        }
        //记录缓存
        $.data(overallCache, 'tableView' + key, cache);
    },
    //设置缓存的值
    callbackCacheValue: function (key, queryForm) {
        //获取缓存
        var cache = $.data(overallCache, 'tableView' + key);
        if (isEmpty(cache) || cache.length == 0) {
            return false;
        }
        if (!isEmpty(queryForm)) {
            //设置select
            queryForm.find('select').each(function () {
                var $select = $(this);
                var val = cache[$select.prop('name')];
                //选中option
                if (!isEmpty(val)) {
                    $select.find('option').each(function () {
                        if ($(this).val() == val) {
                            $select.val(val).trigger('change');
                        }
                    });
                }
            });
            //设置input
            queryForm.find('input').each(function () {
                var $input = $(this);
                var val = cache[$input.prop('name')];
                if (!isEmpty(val)) {
                    $input.val(val).trigger('hide');
                }
            });
        }

        //设置待审已审
        var processStatus = cache['processStatus'];
        if (!isEmpty(processStatus)) {
            var $processAllBtn = $('#processAllBtn');
            var $processStayBtn = $('#processStayBtn');
            var $processAlreadyBtn = $('#processAlreadyBtn');

            var all = $processAllBtn.attr('data-process-status');
            var stay = $processStayBtn.attr('data-process-status');
            var already = $processAlreadyBtn.attr('data-process-status');

            if (processStatus == all) {
                $processStayBtn.removeClass('active');
                $processAlreadyBtn.removeClass('active');
                $processAllBtn.addClass('active');
                $('#processStatus').val(all);
            } else if (processStatus == stay) {
                $processStayBtn.addClass('active');
                $processAlreadyBtn.removeClass('active');
                $processAllBtn.removeClass('active');
                $('#processStatus').val(stay);
            } else if (processStatus == already) {
                $processStayBtn.removeClass('active');
                $processAlreadyBtn.addClass('active');
                $processAllBtn.removeClass('active');
                $('#processStatus').val(already);
            }
        }

        return true;
    },
    //清空缓存
    clearCacheValue: function (key) {
        $.data(overallCache, 'tableView' + key, {});
    },
    //重置搜索框
    resetSerach: function (queryForm) {
        if (queryForm != undefined) {
            //重置select
            queryForm.find('select').each(function () {
                $(this).val('').trigger('change');
                $(this).parent().addClass('is-empty');
            });
            //重置input
            queryForm.find('input').each(function () {
                $(this).val('');
                $(this).parent().addClass('is-empty');
                $(this).parent().children('.glyphicon-remove').hide();
                $(this).parent().children('.mdi-window-close').hide();
            });
        }
    },
    //刷新表单,true返回到第一页，false不会到
    reload: function (table, isFirst) {
        if (isFirst == undefined || isFirst) {
            table.ajax.reload();
        } else {
            table.ajax.reload(null, false);
        }
    },
    //返回列表的data数据
    rowData: function (table, row) {
        return table.row($(row).parents('tr')).data();
    },
    //为行添加class
    addClass: function (table, row, addClass) {
        $(table.row(row).node()).addClass(addClass)
    },
    /**
     * 控制列是否显示
     * @param $table
     * @param isShow true显示 false不显示
     * @param row 列号
     */
    visible: function ($table, isShow, row) {
        $table.column(row).visible(isShow);
    },
    /**
     * 1秒只能点击一次
     */
    btnClick: throttle(function (btn) {
        btn.click();
    }, 1),
    /**
     * 序号
     * @param api
     * @param index
     */
    orderNumber: function (api, index) {
        var startIndex = api.context[0]._iDisplayStart;//获取到本页开始的条数
        api.column(index).nodes().each(function (cell, i) {
            cell.innerHTML = startIndex + i + 1;
        });
    },
    /**
     * 选择框
     * @param api
     * @param index
     */
    choiceBox: function (api, index) {
        api.column(index, {
            search: 'applied',
            order: 'applied'
        }).nodes().each(function (cell, i) {
            cell.innerHTML = '';
        });
    },
    init: function (options) {
        var settings = $.extend({
            info: true,
            paging: true,
            cache: true,
            headLength: true,
            rowId: 'ID',
            pageLength: 10,
            columnDefs: [],
            createdRow: function (row, data, dataIndex) {
            },
        }, options);

        //重置是否搜索
        tableView.isSerach = false;
        //设置缓存的值
        if (settings.cache) {
            if (tableView.callbackCacheValue(options.url, options.queryForm)) {
                tableView.isSerach = true;
            }
        }
        //绘制表格
        var $table = options.object.DataTable({
            //lengthMenu: [5, 10, 20, 30],//这里也可以设置分页，但是不能设置具体内容，只能是一维或二维数组的方式，所以推荐下面language里面的写法。
            serverSide: true,//分页，取数据等等的都放到服务端去
            processing: true,//载入数据的时候是否显示“载入中”
            ordering: false, //排序操作
            paging: settings.paging, //是否开启分页，默认为是
            pagingType: "full_numbers",
            pageLength: settings.pageLength,  //首次加载的数据条数
            stateSave: true,//保持翻页状态，和comTable.fnDraw(false);结合使用
            searching: false,//禁用datatables搜索
            deferRender: true, //当处理大数据时，延迟渲染数据，有效提高Datatables处理能力
            destroy: true,//防止重复初始化
            info: settings.info,
            // rowId: settings.rowId,
            createdRow: settings.createdRow,
            ajax: {
                type: "GET",
                url: options.url,
                dataSrc: "data",
                dataType: "json",
                data: function (d) {
                    //前台进度条
                    if (typeof NProgress != 'undefined') {
                        NProgress.start();
                    }
                    //后台刷新
                    if (options.adminLoadingDiv != undefined) {
                        options.adminLoadingDiv.append(addLoadingDiv());
                    }
                    //搜索开始的方法
                    if (options.startSearchCallback != undefined) {
                        options.startSearchCallback();
                    }

                    var param = {};
                    param.draw = d.draw;
                    param.start = d.start;
                    param.length = d.length;
                    //添加自定义参数
                    if (options.searchParams != undefined) {
                        options.searchParams(param);
                    }
                    //需要定义tableView.isSerach是否搜索
                    if (tableView.isSerach) {
                        var formData = options.queryForm.serializeArray();//把form里面的数据序列化成数组
                        formData.forEach(function (e) {
                            param[e.name] = e.value;
                        });
                    }
                    return param;//自定义需要传递的参数。
                },
            },
            //对应上面thead里面的序列
            columns: options.columns,
            //操作按钮
            columnDefs: settings.columnDefs,
            language: {
                lengthMenu: '<select class="form-control">' + '<option value="5">5</option>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="30">30</option>' + '<option value="40">40</option>' + '<option value="50">50</option>' + '</select>条记录',//左上角的分页大小显示。
                search: '搜索：',//右上角的搜索文本，可以写html标签
                processing: '<i class="mdi mdi-24px mdi-spin mdi-loading"></i>加载中',

                paginate: {//分页的样式内容。
                    previous: "上一页",
                    next: "下一页",
                    first: "第一页",
                    last: "最后",
                    class: "pagination-info"
                },

                zeroRecords: "没有内容",//table tbody内容为空时，tbody的内容。
                //下面三者构成了总体的左下角的内容。
                info: "总共_PAGES_ 页，显示第_START_ 到第 _END_ ，总共 _TOTAL_ 条",//左下角的信息显示，大写的词为关键字。
                infoEmpty: "0条记录",//筛选为空时左下角的显示。
                infoFiltered: ""//筛选之后的左下角筛选提示，
            },
            //在每次table被draw完后回调函数
            fnDrawCallback: function () {
                //搜索完回调
                if (options.endCallback != undefined) {
                    options.endCallback(this.api());
                }
                //前台进度条
                if (typeof NProgress != 'undefined') {
                    NProgress.done();
                }
                //后台刷新
                if (options.adminLoadingDiv != undefined) {
                    removeLoadingDiv();
                }
            },
            fnRowCallback: function (nRow, aData, iDisplayIndex) {
                if (options.fnRowCallback != undefined) {
                    options.fnRowCallback(nRow, aData, iDisplayIndex);
                }
            },
        });

        //切换待审已审
        var $processAllBtn = $('#processAllBtn');
        var $processStayBtn = $('#processStayBtn');
        var $processAlreadyBtn = $('#processAlreadyBtn');

        var all = $processAllBtn.attr('data-process-status');
        var stay = $processStayBtn.attr('data-process-status');
        var already = $processAlreadyBtn.attr('data-process-status');

        $processAllBtn.on('click', throttle(function () {
            $processAllBtn.addClass('active');
            $processStayBtn.removeClass('active');
            $processAlreadyBtn.removeClass('active');
            $('#processStatus').val(all);
            tableView.reload($dataGrid, false);
            tableView.saveCacheValue(options.url, options.queryForm);
        }, 1));
        $processStayBtn.on('click', throttle(function () {
            $processAllBtn.removeClass('active');
            $processStayBtn.addClass('active');
            $processAlreadyBtn.removeClass('active');
            $('#processStatus').val(stay);
            tableView.reload($dataGrid, false);
            tableView.saveCacheValue(options.url, options.queryForm);
        }, 1));
        $processAlreadyBtn.on('click', throttle(function () {
            $processAllBtn.removeClass('active');
            $processStayBtn.removeClass('active');
            $processAlreadyBtn.addClass('active');
            $('#processStatus').val(already);
            tableView.reload($dataGrid, false);
            tableView.saveCacheValue(options.url, options.queryForm);
        }, 1));
        //搜索事件
        if (options.searchBtn != undefined) {
            //1秒只能点击一次
            options.searchBtn.addClass('btn-sm').addClass('disabled-1s');

            options.searchBtn.on('click', function () {
                tableView.isSerach = true;

                $table.ajax.reload();
                //记录缓存
                if (settings.cache) {
                    tableView.saveCacheValue(options.url, options.queryForm);
                }
                //搜索回调
                if (options.searchCallback != undefined) {
                    options.searchCallback();
                }
            });
            //绑定回车事件
            $(document).off('keyup');
            $(document).on('keyup', function (event) {
                if (event.keyCode == 13) {
                    tableView.btnClick(options.searchBtn);
                }
            });
        }
        //重置按钮事件
        if (options.resetBtn != undefined) {
            //0.5秒只能点击一次
            options.resetBtn.addClass('btn-sm').addClass('disabled-0.5s');

            options.resetBtn.on('click', function () {
                tableView.isSerach = false;
                tableView.resetSerach(options.queryForm);
                //清空缓存
                if (settings.cache) {
                    tableView.clearCacheValue(options.url);
                }
                //重置回调
                if (options.resetCallback != undefined) {
                    options.resetCallback();
                }
            });
        }
        //刷新按钮
        if (options.refreshBtn != undefined) {
            //1秒只能点击一次
            options.refreshBtn.addClass('disabled-1s');

            options.refreshBtn.on('click', function () {
                $table.ajax.reload(null, false);
                //刷新回调
                if (options.refreshCallback != undefined) {
                    options.refreshCallback();
                }
            });
        }
        //清空选择按钮
        if (options.clearBtn != undefined) {
            //1秒只能点击一次
            options.clearBtn.addClass('disabled-1s');

            options.clearBtn.on('click', function () {
                $table.$('tr.selected').removeClass('selected');
            });
        }
        //设置
        if (options.setting != undefined) {
            options.setting($table);
        }
        //关闭表格的头
        if (options.headLength == false) {
            $()
        }
        //设置样式
        $('.card .material-datatables label').addClass('form-group');

        return $table;
    }
}
/**
 * 验证
 * @type {{validationDate: validator.validationDate, formValidate: validator.formValidate, fieidValidate: validator.fieidValidate, init: validator.init}}
 */
validator = {
    //验证2个日期大小
    validationDate: function (startTime, endStart) {
        var startTime = new Date(Date.parse(startTime));
        var endTime = new Date(Date.parse(endStart));

        return startTime < endTime;
    },
    //验证表单
    formValidate: function (form) {
        form.data('bootstrapValidator').validate();
        return form.data("bootstrapValidator").isValid();
    },
    //验证字段
    fieidValidate: function (form, id) {
        form.data('bootstrapValidator')
            .updateStatus(id, 'NOT_VALIDATED', null)
            .validateField(id);
    },
    init: function (options) {
        var $form = options.form.bootstrapValidator({
            language: "zh_CN",
            message: '这是无效的',
            live: 'enabled',
            feedbackIcons: {
                valid: 'mdi mdi-check',
                invalid: 'mdi mdi-close',
                validating: 'mdi mdi-refresh'
            },
            fields: options.fields == undefined ? {} : options.fields,
        });

        return $form;
    }
}
/**
 * 模态框
 * @type {{size: {LG: string, SM: string, NONE: string}, class: {DEFAULT: string, PRIMARY: string, INFO: string, WARNING: string, SUCCESS: string, DANGER: string}, btnName: {DEL: string, SAVE: string, CLOSE: string, OK: string, RESET: string, SUBMIT: string, BACK: string, WITHDRAW: string}, footerModel: {ADMIN: string, MY_HOME: string}, show: Function, hide: model.hide, confirm: model.confirm}}
 */
model = {
    //模态框大小
    size: {LG: 'modal-lg', SM: 'modal-sm', NONE: ''},
    //模态框样式
    class: {
        DEFAULT: '',
        PRIMARY: 'modal-primary',
        INFO: 'modal-info',
        WARNING: 'modal-warning',
        SUCCESS: 'modal-success',
        DANGER: 'modal-danger'
    },
    //按钮名字
    btnName: {DEL: '删除', SAVE: '保存', CLOSE: '关闭', OK: '确定', RESET: '重置', SUBMIT: '提交', BACK: '退回', WITHDRAW: '撤回'},
    //底部的样式
    footerModel: {ADMIN: 'admin', MY_HOME: 'my_home'},
    //显示,1秒内只能执行一次防止多次点击
    show: throttle(function (options) {
        var settings = $.extend({
            isConfirm: false,
            okBtnName: model.btnName.SAVE,
            closeBtnName: model.btnName.CLOSE
        }, options);

        //对象
        var id = uuid();
        var confirmBtnId = 'confirm' + id;
        var cancelBtnId = 'cancel' + id;
        var jquery = '#' + id;
        var okBtnName = settings.okBtnName;
        var closeBtnName = settings.closeBtnName;

        var footer = '';

        if (!isEmpty(settings.footerModel) && settings.footerModel == model.footerModel.ADMIN) {
            //按钮样式
            var okBtnDefaultClass = settings.class == undefined ? 'btn-primary' : 'btn-outline';
            var closeBtnDefaultClass = settings.class == undefined ? 'btn-default' : 'btn-outline';
            //确定按钮不显示的话关闭显示在右边
            var closeBtnPosition = settings.isConfirm == undefined || settings.isConfirm == true ? 'pull-left' : 'pull-right';

            footer = '<button type="button" class="btn ' + closeBtnDefaultClass + ' ' + closeBtnPosition + '" data-dismiss="modal">' + closeBtnName + '</button>' +
                '<button type="button" class="btn ' + okBtnDefaultClass + ' model-ok" id="' + confirmBtnId + '">' + okBtnName + '</button>';
        } else {
            footer = '<button type="button" class="btn btn-success btn-simple model-ok" id="' + confirmBtnId + '">' + okBtnName + '</button>' +
                '<button type="button" class="btn btn-danger btn-simple" data-dismiss="modal">' + closeBtnName + '</button>'
        }


        var html = '' +
            '<div class="modal model-custom fade ' + settings.class + '" id="' + id + '" style="z-index: 10000;" tabindex="-1" role="dialog" aria-labelledby="' + id + 'Label" aria-hidden="true">' +
            '  <div class="modal-dialog ' + settings.size + '">' +
            '    <div class="modal-content">' +
            '      <div class="modal-header">' +
            '        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">' +
            '          <i class="mdi mdi-close"></i>' +
            '        </button>' +
            '        <h4 class="modal-title">' + settings.title + '</h4>' +
            (isEmpty(settings.tips) ? '' : '<div><p style="color: red;">提示:' + settings.tips + '</p></div>') +
            '      </div>' +
            '      <div class="modal-body">' + settings.content + '</div>' +
            '      <div class="modal-footer">' + footer + '</div>' +
            '    </div>' +
            '  </div>' +
            '</div>';

        //添加
        $('body').append(html);
        //HTML添加完成回调
        if (settings.loadContentComplete != undefined) {
            settings.loadContentComplete();
        }
        //设置按钮
        if (settings.footerModel == undefined || settings.footerModel == model.footerModel.MY_HOME) {
            $('#' + id + ' .btn-primary').removeClass('btn-primary').addClass('btn-info');
        }
        //设置参数
        $(jquery).modal({
            keyboard: true,
            backdrop: 'static'
        });
        $(jquery).modal(settings.options);
        //是否显示确定按钮
        if (settings.isConfirm != undefined && settings.isConfirm == false) {
            $('#' + confirmBtnId).remove();
        }
        //完成加载事件
        if (settings.onLoadSuccess != undefined) {
            settings.onLoadSuccess(jquery);
        }
        //解决select2不能输入搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
        //弹窗
        $(jquery).modal('show');
        //确定事件
        $('#' + confirmBtnId).on('click', function () {
            if (settings.confirm != undefined) {
                settings.confirm(jquery);
            }
        });
        //是否垂直居中
        if (!isEmpty(options.vertical) && options.vertical == true) {
            $(jquery).on("shown.bs.modal", function () {
                // 是弹出框居中。。。
                var $modal_dialog = $(this).find('.modal-dialog');
                //获取可视窗口的高度
                var clientHeight = (document.body.clientHeight < document.documentElement.clientHeight) ? document.body.clientHeight : document.documentElement.clientHeight;
                //得到dialog的高度
                var dialogHeight = $modal_dialog.height();
                //计算出距离顶部的高度
                var m_top = (clientHeight - dialogHeight) / 2;

                // $modal_dialog.css({'margin': m_top + 'px auto'});

                // $modal_dialog.animate({
                //     marginTop:m_top,
                // },200);
            });
        }

        //取消事件,移除model
        $(jquery).on('hidden.bs.modal', function () {
            if (settings.cancel != undefined) {
                settings.cancel();
            }
            $(jquery).remove();
            // 解决多层模态框关闭导致下一层不能滚动
            if ($('.model-custom').length > 0) {
                $(document.body).addClass("modal-open");
            }
        });

    }, 1),
    //隐藏,1秒内只能执行一次防止多次点击
    hide: function (model) {
        if (typeof model == 'undefined') {
            $('.modal').modal('hide');
        } else {
            $(model).modal('hide');
        }
    },
    confirm: function (options) {
        var settings = $.extend({
            title: '操作提示',
            message: "是否确定?",
        }, options);
        var isClick = false;
        model.show({
            title: settings.title,
            content: settings.message,
            size: model.size.SM,
            //垂直居中
            vertical: true,
            okBtnName: model.btnName.OK,
            isConfirm: true,
            footerModel: window.location.href.indexOf(MANAGER_URL) != -1 ? model.footerModel.ADMIN : model.footerModel.MY_HOME,
            confirm: function ($model) {
                if (!isEmpty(options.callback)) {
                    options.callback(true);
                }
                isClick = true;
                model.hide($model);
            },
            cancel: function () {
                if (!isClick && !isEmpty(options.callback)) {
                    options.callback(false);
                }
            }
        });
    }
}

/**
 * 选择搜索列表
 * @type {{mode: {MULTIPLE: string, SINGLE: string}, init: choiceBox.init}}
 */
choiceBox = {
    mode: {MULTIPLE: 'MULTIPLE', SINGLE: 'SINGLE'},
    init: function (options) {
        var settings = $.extend({
            title: '请选择',
            selectMode: choiceBox.mode.SINGLE,
            fields: [],
            searchFields: [],
            searchLabel: '名称',
            searchParams: {},
            url: '',
            modelSize: model.size.NONE,
            footerModel: model.footerModel.ADMIN,
        }, options);

        //初始化table
        var $table;
        //初始化字段
        var tableId = uuid();
        var tableFields = '';
        //tabel查询字段
        var tableDatas = [];
        //添加序号字段
        settings.fields.unshift({min_width: 30, name: '序号', data: null});

        for (var i in settings.fields) {
            var map = settings.fields[i];
            //最小宽度
            var min_width = (isEmpty(map.min_width) ? 120 : map.min_width) + 'px';
            //字段名
            var name = map.name;
            tableFields += '<th style="min-width: ' + min_width + ';">' + name + '</th>';

            tableDatas.push({data: map.data});
        }

        var tableClass = tableDatas.length > 2 ? 'table-overflow-x' : '';
        var tableContent = '<table id="' + tableId + '" class="table table-bordered table-striped ' + tableClass + '" style="width: 100%"><thead><tr>' + tableFields + '</tr></thead></table>';

        //初始化搜索框
        var searchInputs = '';
        if (isEmpty(settings.searchFields)) {
            searchInputs = '<div class="form-group form-group-search" style="width: 100%;"><label for="name" class="col-sm-2 control-label">' + settings.searchLabel + '</label><div class="col-sm-10"><input name="name" type="text" class="form-control form-control-input-search"></div></div>';
        } else {
            for (var i in settings.searchFields) {
                var map = settings.searchFields[i];
                //label名
                var label = isEmpty(map.label) ? map.name : map.label;
                //提交参数的名字
                var name = map.name;
                // 扩展input的参数
                var extend = map.extend;

                searchInputs += '<div class="form-group form-group-search"><label for="' + name + '" class="col-sm-4 control-label">' + label + '</label><div class="col-sm-8"><input name="' + name + '" type="text" class="form-control form-control-input-search" ' + extend + '></div></div>';
            }
        }

        //搜索按钮
        var okBtnId = uuid();
        var resBtnId = uuid();
        var searchBtn = '<div class="btn-group-search"><button type="button" class="btn btn-success"  id="' + okBtnId + '">搜索</button><button type="button" class="btn btn-danger" id="' + resBtnId + '">重置</button></div>';

        //搜索框ID
        var formId = uuid();
        //拼接HTML
        var html = '<div class=""><form id="' + formId + '" class="form-horizontal">' + searchInputs + searchBtn + '</form>' + tableContent + '</div>';

        var selectMode = '';
        if (settings.selectMode == choiceBox.mode.SINGLE) {
            selectMode = '(单选)';
        } else if (settings.selectMode == choiceBox.mode.MULTIPLE) {
            selectMode = '(多选)';
        }

        model.show({
            title: settings.title + selectMode,
            content: html,
            footerModel: settings.footerModel,
            size: settings.modelSize,
            //HTML加载完成回调
            loadContentComplete: function () {
                $table = tableView.init({
                    //table对象
                    object: $('#' + tableId),
                    //搜索按钮
                    searchBtn: $('#' + okBtnId),
                    //重置按钮
                    resetBtn: $('#' + resBtnId),
                    //查询提交的表单
                    queryForm: $('#' + formId),
                    //查询URL链接
                    url: settings.url,
                    //对应上面thead里面的序列
                    columns: tableDatas,
                    //关闭缓存
                    cache: false,
                    //添加搜索参数
                    searchParams: function (param) {
                        for (var i in settings.searchParams) {
                            param[i] = settings.searchParams[i];
                        }
                    },
                    endCallback: function () {
                        $table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                            cell.innerHTML = i + 1;
                        });
                    },
                    //设置
                    setting: function ($table) {
                        //设置选择模式
                        if (settings.selectMode == choiceBox.mode.SINGLE) {
                            //设置单选
                            $('#' + tableId + ' tbody').on('click', 'tr', function () {
                                if ($(this).hasClass('selected')) {
                                    $(this).removeClass('selected');
                                } else {
                                    $table.$('tr.selected').removeClass('selected');
                                    $(this).addClass('selected');
                                }
                            });

                        } else if (settings.selectMode == choiceBox.mode.MULTIPLE) {
                            //设置多选
                            $('#' + tableId + ' tbody').on('click', 'tr', function () {
                                $(this).toggleClass('selected');
                            });
                        }
                    }
                    ,
                })
                ;
            },
            //点击回调
            confirm: function (model) {
                options.confirm(model, $table.rows('.selected').data());
            }
        });
    }
}

/**
 * 树菜单选择
 * @type {{mode: {MULTIPLE: string, SINGLE: string}, init: treeBox.init, create: treeBox.create, search: treeBox.search, toggleSearch: treeBox.toggleSearch}}
 */
treeBox = {
    mode: {MULTIPLE: 'MULTIPLE', SINGLE: 'SINGLE'},
    init: function (options) {
        var settings = $.extend({
            title: '请选择',
            class: '',
            selectMode: treeBox.mode.MULTIPLE,
            showIcon: false,
            showCheckbox: true,
            url: '',
            searchParams: {},
            modelSize: model.size.NONE,
            footerModel: model.footerModel.ADMIN,
        }, options);

        //设置参数
        //treeid
        var treeId = uuid();
        var searchInputId = uuid();
        var tree = '#' + treeId;
        var searchInput = '#' + searchInputId;
        settings.tree = tree;
        //HTML主内容
        var html = '';
        //搜索DIV
        var serachDiv = '<div class="row"><div class="form-group form-group-search" style="width: 100%;"><div class="col-sm-9"><input id="' + searchInputId + '" type="text" class="form-control form-control-input-search" placeholder="搜索内容...."></div><div class="col-sm-3 btn-group btn-single-group" align="center"><button id="' + searchInputId + 'btn" class="btn btn-success btn-single-search" onclick="treeBox.search(\'' + tree + '\',$(\'' + searchInput + '\').val())">搜索</button><button class="btn btn-info btn-single-search" onclick="treeBox.toggleSearch(\'' + tree + '\',$(\'' + searchInput + '\').val())">切换</button></div></div></div>';
        //表单
        var table = '<div id="' + treeId + '" class="' + settings.class + '" style="height:500px;overflow:auto;overflow-x:hidden;"></div>';
        html += serachDiv + table;

        var selectMode = '';
        if (settings.selectMode == treeBox.mode.SINGLE) {
            selectMode = '(单选)';
        } else if (settings.selectMode == treeBox.mode.MULTIPLE) {
            selectMode = '(多选)';
        }

        ajax.get(settings.url, settings.searchParams, function (data) {
            model.show({
                title: settings.title + selectMode,
                content: html,
                footerModel: settings.footerModel,
                size: settings.modelSize,
                isConfirm: settings.isConfirm,
                //HTML加载完成回调
                loadContentComplete: function () {
                    //创建树菜单
                    settings.data = data;
                    treeBox.create(settings);
                    //监听输入框回车事件
                    $(searchInput).bind('keypress', function (event) {
                        if (event.keyCode == 13) {
                            $(searchInput + 'btn').click();
                        }
                    });
                },
                //点击回调
                confirm: function (model) {
                    options.confirm(model, $(tree).treeview('getChecked'));
                }
            });
        });
    },
    //创建树
    create: function (options) {
        var $tree = $(options.tree);
        $tree.treeview({
            data: options.data,
            showIcon: options.showIcon,
            showCheckbox: options.showCheckbox,
            showTags: true,
            //选中节点
            onNodeChecked: function (event, node) {
                if (options.onNodeChecked != undefined) {
                    options.onNodeChecked(event, node);
                }
                //单选模式
                if (options.selectMode == treeBox.mode.SINGLE) {
                    //取消其他选中
                    var checkeds = $tree.treeview('getChecked');
                    for (var i = 0; i < checkeds.length; i++) {
                        if (checkeds[i].nodeId != node.nodeId) {
                            $tree.treeview('uncheckNode', [checkeds[i], {silent: true}]);
                        }
                    }
                }
            },
            //取消选中节点
            onNodeUnchecked: function (event, node) {
                if (options.onNodeUnchecked != undefined) {
                    options.onNodeUnchecked(event, node);
                }
            },
            //选中
            onNodeSelected: function (event, node) {
                if (options.selectMode == treeBox.mode.SINGLE) {
                    if (options.isSelectCheck == undefined || options.isSelectCheck == true) {
                        $tree.treeview('toggleNodeChecked', [node.nodeId, {silent: false}]);
                    }
                }

                if (options.onNodeSelected != undefined) {
                    options.onNodeSelected(event, node);
                }
            },
            //取消选中
            onNodeUnselected: function (event, node) {
                if (options.selectMode == treeBox.mode.SINGLE) {
                    if (options.isSelectCheck == undefined || options.isSelectCheck == true) {
                        $tree.treeview('toggleNodeChecked', [node.nodeId, {silent: false}]);
                    }
                }

                if (options.onNodeUnselected != undefined) {
                    options.onNodeUnselected(event, node);
                }
            }
        });
        var checkeds = $tree.treeview('getChecked');
        for (var i = 0; i < checkeds.length; i++) {
            $tree.treeview('revealNode', [checkeds[i].nodeId, {silent: true}]);
        }
        return $tree;
    },
    /**
     * 搜索
     * @param tree
     * @param text
     */
    search: function (tree, text) {
        var $tree = $(tree);
        return $tree.treeview('search', [text, {
            ignoreCase: false,
            exactMatch: false
        }]);
    },
    /**
     * 切换搜索
     * @param tree
     * @param text
     */
    toggleSearch: function (tree, text) {
        var $tree = $(tree);
        $tree.treeview('toggleNodeChecked', [treeBox.search(tree, text), {silent: false}]);
    }
}


/**
 * ajax方法
 * @type {{init: ajax.init, setup: ajax.setup, start: ajax.start, success: ajax.success, stop: ajax.stop, getHtml: ajax.getHtml, get: ajax.get, post: ajax.post, put: ajax.put, del: ajax.del, file: ajax.file}}
 */
ajax = {
    // ajax设置
    init: function () {
        //启动调用
        $(document).ajaxStart(function () {
            ajax.start();
            NProgress.done();
            NProgress.start();
        });
        //成功调用
        $(document).ajaxSuccess(function () {
            ajax.success();
        });
        //停止调用
        $(document).ajaxStop(function () {
            ajax.stop();
            NProgress.done();
        });
        $(document).ajaxComplete(function () {
            ajax.complete();
        });
    },
    setup: function (options) {
        $.ajaxSetup({
            //禁止缓存
            cache: false,
            //60秒超时
            timeout: 60000,
            complete: function (XMLHttpRequest, textStatus) {
                // 通过XMLHttpRequest取得响应头，sessionstatus，
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                if (sessionstatus == "timeout") {
                    alert("登录超时,请重新登录！");
                    // 果超时就处理 ，指定要跳转的页面
                    window.location.href = options.logoutUrl == undefined ? BASE_URL + 'logout' : options.logoutUrl;
                }
            },
            error: function (jqXHR, textStatus, errorMsg) { // 出错时默认的处理函数
                // jqXHR 是经过jQuery封装的XMLHttpRequest对象
                // textStatus 可能为： null、"timeout"、"error"、"abort"或"parsererror"
                // errorMsg 可能为： "Not Found"、"Internal Server Error"等
                // 提示形如：发送AJAX请求到"/index.html"时出错[404]：Not Found
                ajax.stop();
                NProgress.done();
                if (typeof(removeLoadingDiv) == 'function') {
                    removeLoadingDiv();
                }
                //ajax访问超时ss
                if (textStatus == 'timeout') {
                    demo.showNotify(ALERT_WARNING, '访问服务器超时!');
                } else {
                    demo.showNotify(ALERT_WARNING, '发送AJAX请求到"' + this.url + '"时出错[' + jqXHR.status + ']：' + errorMsg);
                }
            }
        });
    },
    //开始AJAX时候调用的代码
    start: function () {
        //所有点击model提交的时候防止多次重复提交
        $('button.model-ok').prop('disabled', true);
    },
    //成功时候调用的代码
    success: function () {
        // notifyMsg.delayCount();
        //刷新token

    },
    //停止的时候调用的代码
    stop: function () {
        //所有点击model提交的时候防止多次重复提交
        $('button.model-ok').prop('disabled', false);
        removeLoadingDiv();
    },
    complete: function () {

    },
    getHtml: function (url, params, callback) {
        var headers = {html: "true"};
        //是否是pjax请求
        if (!isEmpty(params['X-PJAX'])) {
            headers['X-PJAX'] = true;
        }
        return $.ajax({
            headers: headers,
            type: "GET",
            url: url,
            data: params,
            success: function (html) {
                //成功
                callback(html);
            }
        });
    },
    get: function (url, params, callback) {
        return $.ajax({
            type: "GET",
            url: url,
            data: params,
            dataType: "json",
            success: function (data) {
                //成功
                if (typeof data === "string") {
                    data = $.parseJSON(data);
                }
                callback(data);
            },
        });
    },
    post: function (url, params, callback) {
        return $.ajax({
            type: "POST",
            url: url,
            data: params,
            dataType: "json",
            success: function (data) {
                //成功
                if (typeof data === "string") {
                    data = $.parseJSON(data);
                }
                callback(data);
            },
        });
    },
    put: function (url, params, callback) {
        return $.ajax({
            type: "PUT",
            url: url,
            data: params,
            dataType: "json",
            success: function (data) {
                //成功
                if (typeof data === "string") {
                    data = $.parseJSON(data);
                }
                callback(data);
            },
        });
    },
    del: function (url, params, callback) {
        return $.ajax({
            type: "DELETE",
            url: url,
            data: params,
            dataType: "json",
            success: function (data) {
                //成功
                if (typeof data === "string") {
                    data = $.parseJSON(data);
                }
                callback(data);
            },
        });
    },
    /**
     * 文件上传
     * @param url
     * @param form form表单
     * @param callback
     */
    file: function (url, form, callback) {
        return form.ajaxSubmit({
            type: 'POST',
            url: url,
            dataType: "json",
            success: function (data) {
                if (typeof data === "string") {
                    data = $.parseJSON(data);
                }
                callback(data);
            }
        });
    }
}

/**
 * AJAX返回通用提示
 * @type {{data: ajaxReturn.data, tree: ajaxReturn.tree}}
 */
ajaxReturn = {
    /**
     * 提示，成功后关闭模态框和刷新列表
     * dataGrid使用
     * @param data
     * @param table
     * @param isFirst true 回到第一页，false页码不动
     */
    data: function (data, $model, table, isFirst, options) {
        //设置token
        if (!isEmpty(data.token)) {
            $('input[name="' + SUBMIT_TOKEN_NAME + '"]').val(data.token);
        }

        if (data.code == STATUS_SUCCESS) {

            if (!isEmpty(data.message)) {
                demo.showNotify(ALERT_SUCCESS, data.message);
            }
            //关闭模态框
            if (!isEmpty($model)) {
                $($model).modal('hide');
            }
            //刷新列表
            if (!isEmpty(table)) {
                tableView.reload(table, isFirst);
            }
            if (!isEmpty(options) && !isEmpty(options.success)) {
                options.success(data);
            }
        } else {
            if (!isEmpty(data.message)) {
                demo.showNotify(ALERT_WARNING, data.message);
            }
            if (!isEmpty(options) && !isEmpty(options.error)) {
                options.error(data);
            }
        }
    },
    /**
     * 提示，成功后关闭模态框和刷新列表
     * treeGrid使用
     * @param data
     * @param table
     */
    tree: function (data, $model, table, options) {
        //设置token
        if (!isEmpty(data.token)) {
            $('input[name="' + SUBMIT_TOKEN_NAME + '"]').val(data.token);
        }

        if (data.code == STATUS_SUCCESS) {
            if (!isEmpty(data.message)) {
                demo.showNotify(ALERT_SUCCESS, data.message);
            }
            //关闭模态框
            if (!isEmpty($model)) {
                $($model).modal('hide');
            }
            //刷新列表
            if (!isEmpty(table)) {
                table.bootstrapTable('refresh', {silent: false});
            }
            if (!isEmpty(options) && !isEmpty(options.success)) {
                options.success(data);
            }
        } else {
            if (!isEmpty(data.message)) {
                demo.showNotify(ALERT_WARNING, data.message);
            }
            if (!isEmpty(options) && !isEmpty(options.error)) {
                options.error(data);
            }
        }
    }
}
/**
 * 日期初始化器
 * @type {{model: {YMD: string, YM: string, Y: string}, format: {YYYYMMDD: string, YYYYMM: string, YYYY: string}, init: datepick.init}}
 */
datepick = {
    model: {YMDHI: 'yyyy-MM-dd HH:ii', YMD: 'yyyy-mm-dd', YM: 'yyyy-mm', Y: 'yyyy'},
    format: {YYYYMMDDHHII: 'yyyy-MM-dd HH:ii', YYYYMMDD: 'yyyy-mm-dd', YYYYMM: 'yyyy-mm', YYYY: 'yyyy'},
    init: function (options) {
        var settings = $.extend({
            obj: '',
            format: datepick.format.YYYYMMDD,
            startView: 4,
            minView: 4,
            maxViewMode: 4,
            minViewMode: 4,
            weekStart: 1,
            todayBtn: 'linked',
        }, options);

        if (!isEmpty(options.model)) {
            if (options.model == datepick.model.Y) {
                settings.startView = 2;
                settings.minView = 2;
                settings.maxViewMode = 2;
                settings.minViewMode = 2;
                settings.format = datepick.format.YYYY;
            } else if (options.model == datepick.model.YM) {
                settings.startView = 1;
                settings.minView = 1;
                settings.maxViewMode = 1;
                settings.minViewMode = 1;
                settings.format = datepick.format.YYYYMM;
            } else if (options.model == datepick.model.YMDHI) {
                settings.startView = 5;
                settings.minView = 5;
                settings.maxViewMode = 5;
                settings.minViewMode = 5;
                settings.format = datepick.format.YYYYMMDDHHII;
            }
        }

        var date = options.obj.datepicker({
            format: settings.format,
            weekStart: settings.weekStart,
            autoclose: true,
            startView: settings.startView,
            minView: settings.minView,
            forceParse: false,
            todayBtn: settings.todayBtn,
            maxViewMode: settings.maxViewMode,
            minViewMode: settings.minViewMode,
            language: 'zh-CN'
        })
        //日期清除
        if (!isEmpty(options.clear) && options.clear == true) {
            options.obj.dateClear();
        }

        return date;
    }
}
/**
 * 班级选择
 * @type {{model: {NAME: string, VAL: string}, init: classSwitch.init}}
 */
classSwitch = {
    model: {NAME: 'NAME', VAL: 'VAL'},
    init: function (options) {
        var $departmentObj = options.dObj;
        var $classObj = options.cObj;
        var dVal = options.dVal;
        var val = options.val;
        var model = isEmpty(options.model) ? classSwitch.model.VAL : options.model;

        $departmentObj.on('change', function () {
            var departmentName = $(this).val();
            if (departmentName != "") {
                var params = {};
                if (model == classSwitch.model.VAL) {
                    params.departmentId = departmentName;
                } else {
                    params.departmentName = departmentName;
                }

                var url = model == classSwitch.model.VAL ? CLASS_SELECT_LIST : CLASS_SELECT_NAME_LIST;

                ajax.getHtml(url, params, function (html) {
                    $classObj.html(html);
                    if (!isEmpty(val)) {
                        $classObj.val(val).trigger('change');
                    }
                });
            } else {
                $classObj.html('<option value="" selected>请先选择系部</option>');
            }
        });

        if (!isEmpty(dVal)) {
            $departmentObj.val(dVal).trigger('change');
        }
    }
}
/**
 * 通知
 * @type {{model: {ADMIN: string, RECEPTION: string}, init: notifyMsg.init, obtainCount: notifyMsg.obtainCount, delayCount: Function}}
 */
notifyMsg = {
    model: {ADMIN: 'ADMIN', RECEPTION: 'RECEPTION'},
    //初始化值
    init: function (options) {
        var url = '';
        if (isEmpty(options.model) || options.model == notifyMsg.model.ADMIN) {
            url = MANAGER_URL + 'msg/list';
        } else {

        }

        wpt.init({
            initModel: true,
            content: options.content,
            url: url
        });
    },
    //获取通知条数
    obtainCount: function (options) {
        var settings = $.extend({
            numberObj: $('#notifyNumSpan'),
            tipsObj: $('#notifyNumLi'),
        }, options);

        ajax.get(MANAGER_URL + 'msg/unreadCount', {}, function (data) {
            var unread = data.unreadCount;
            settings.numberObj.text(unread);
            settings.tipsObj.text('你有' + unread + '条新的通知');
        });
    },
    //获取通知条数,30秒一次
    delayCount: throttle(function (options) {
        notifyMsg.obtainCount(options);
    }, 30),
}
/**
 * 滚动监听
 * @type {{start: number, content: Jquery, url: string, params: {}, initModel: boolean, init: wpt.init, next: wpt.next, Infinite: wpt.Infinite}}
 */
wpt = {
    //一页多少条
    pageLength: 10,
    //页数
    start: 1,
    //内容Jquery
    content: $('.infinite-container')[0],
    //链接
    url: '',
    //参数
    params: {},
    //是否是初始化模式
    initModel: false,
    //初始化
    init: function (options) {
        //页数
        wpt.start = 0;
        wpt.content = options.content;
        wpt.url = options.url;
        wpt.params = isEmpty(options.params) ? {} : options.params;
        wpt.initModel = isEmpty(options.initModel) ? false : options.initModel;
        wpt.params.start = wpt.start;

        ajax.getHtml(options.url, wpt.params, function (html) {
            if (wpt.initModel) {
                //替换
                wpt.content.html(html);
                wpt.initModel = false;
            } else {
                //添加
                wpt.content.append(html);
            }
            wpt.start += wpt.pageLength;
        });

        return wpt;
    },
    //下一页继续加载
    next: function () {
        wpt.params.start = wpt.start;
        ajax.getHtml(wpt.url, wpt.params, function (html) {
            wpt.content.append(html);
            wpt.start += wpt.pageLength;
        });
    },
    //滚动到底部监听回调
    Infinite: function (options) {
        var $obj = isEmpty(options.obj) ? $('.infinite-container')[0] : options.obj;
        return new Waypoint.Infinite({
            element: $obj,
            onBeforePageLoad: function () {
                //默认调用下一页
                wpt.next();
                if (!isEmpty(options.onBeforePageLoad)) {
                    options.onBeforePageLoad();
                }
            }
        });
    }
}

/**
 * 文件上传
 * @type {{init: file.init, validate: file.validate}}
 */
file = {
    init: function (options) {
        var settings = $.extend({
            id: '#file',
            //主题
            theme: '',
            //额外上传参数
            uploadExtraData: {},
            //上传URL
            uploadUrl: FILE_UPLOAD,
            //异步上传
            showUpload: true,
            showRemove: true,
            //允许上传的文件
            allowedFileExtensions: ['jpg', 'png', 'jpeg'],
            //最大允许上传文件大小
            maxFileSize: 4000,
            //最大允许上传数量
            maxFilesNum: 10,
            //最大允许同时上传数量
            maxFileCount: 10,
            //预览文件
            initialPreview: [],
            //预览文件配置
            initialPreviewConfig: [],
        }, options);

        return $(settings.id).fileinput({
            theme: settings.theme,
            language: 'zh', //设置语言
            uploadUrl: settings.uploadUrl, //
            uploadExtraData: settings.uploadExtraData,
            showUpload: settings.showUpload, //是否显示上传按钮
            showRemove: settings.showRemove,
            showUploadedThumbs: true,
            showDownload: true,
            allowedFileExtensions: settings.allowedFileExtensions,
            overwriteInitial: false,
            removeFromPreviewOnError: false,
            maxFileSize: settings.maxFileSize,
            maxFilesNum: settings.maxFilesNum,
            showCaption: true,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            maxFileCount: settings.maxFileCount, //表示允许同时上传的最大文件个数
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
            msgAjaxError: "操作失败！请重新尝试！",
            previewFileIcon: "<i class='mdi mdi-file'></i>",
            initialPreview: settings.initialPreview,
            initialPreviewConfig: settings.initialPreviewConfig,
            slugCallback: function (filename) {
                return filename.replace('(', '_').replace(']', '_');
            }
        }).on("filebatchselected", function (event, files) {
            if (!settings.showUpload) {
                //隐藏自带的上传
                $(".kv-file-upload.btn.btn-kv.btn-default.btn-outline-secondary").remove();
            }
        }).on('filebeforedelete', function () {
            return new Promise(function (resolve, reject) {
                model.confirm({
                    message: '是否删除文件?删除后不可恢复!',
                    callback: function (result) {
                        var aborted = !result;
                        if (aborted) {
                            reject(aborted);
                        } else {
                            resolve(aborted);
                        }
                    }
                });
            });
        }).on('filedeleted', function (event, key, jqXHR, data) {
            demo.showNotify(ALERT_SUCCESS, '删除文件' + data.caption + '成功!');
        });
    },
    //验证方法,返回object对象，flag ture为通过验证 false没有通过有message消息
    validate: function () {
        var resultObj = {flag: true, message: ''};
        var message = '';
        $('.file-validate').each(function () {
            if (Number($(this).text()) == 0) {
                message += '请检查附件(' + $(this).attr('data-validate-message') + '),必须上传附件!<br/>';
            }
        });
        if (!isEmpty(message)) {
            resultObj = {flag: false, message: message};
        }
        return resultObj;
    }
}

//支持上传的图片格式
var imgSuffix = ['.jpg', '.png', '.gif', '.jpeg'];
/**
 * 编辑器
 * @type {{fieldName: string, initEdit: editText.initEdit, getTinyMceContent: editText.GetTinyMceContent, setTinyMceContent: editText.GetTinyMceContent}}
 */
editText = {
    fieldName: '',
    initEdit: function (options) {
        var settings = $.extend({
            url: "",
            form: "#form",
            imgInput: "#imgInput",
            maxSize: 10 * 1024,
            maxSizeTips: "图片不能大于10M",
            height: 500
        }, options);

        var $imgInput = $(settings.imgInput);
        var $form = $(settings.form)

        $imgInput.on('change', function () {
            uploadImg(settings.url, $imgInput, $form, settings.maxSize, settings.maxSizeTips);
        });

        tinymce.remove();
        tinymce.init({
            selector: 'textarea',
            height: settings.height,
            theme: 'modern',
            plugins: [
                'advlist autolink lists link image charmap print preview hr anchor pagebreak',
                'searchreplace wordcount visualblocks visualchars code fullscreen',
                'insertdatetime media nonbreaking save table contextmenu directionality',
                'emoticons template paste textcolor colorpicker textpattern imagetools codesample toc help'
            ],
            toolbar1: 'undo redo | insert | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
            toolbar2: 'print preview | forecolor backcolor emoticons | codesample help',
            // media
            image_advtab: true,
            content_css: [],
            file_browser_callback: function (field_name, url, type, win) {
                if (type == 'image') {
                    editText.fieldName = field_name;
                    $imgInput.click();
                }
            }
        });
    },
    getTinyMceContent: function GetTinyMceContent(editorId) {
        return tinyMCE.get(editorId).getContent();
    },
    setTinyMceContent: function GetTinyMceContent(editorId, content) {
        return tinyMCE.get(editorId).setContent(content);
    }
}

//上传图片
function uploadImg(url, input, form, maxSize, maxSizeTips) {
    var mime = suffix($(input).val()).join("").toLowerCase();
    if (mime != ".jpg" && mime != ".png" && mime != ".gif" && mime != ".jpeg") {
        demo.showNotify(ALERT_WARNING, "图片只支持jpg、png、gif、jpeg!");
        return;
    }
    if (findSize(input.prop("id")) > maxSize) {
        demo.showNotify(ALERT_WARNING, maxSizeTips);
        return;
    }
    ajax.file(url, form, function (data) {
        ajaxReturn.data(data, null, null, null, {
            success: function (json) {
                $("#" + editText.fieldName).val(IMG_URL + json.defaultUrl);
            }
        });
    });
}


/**
 * 添加时间选择清除按钮
 */
$.fn.dateClear = function () {
    //设置图片
    var i = '<i class="form-control-feedback mdi mdi-window-close text-red" style="pointer-events:auto;cursor:pointer;" data-bv-icon-for="' + $(this).prop('id') + '"></i>';
    $(this).css('background-color', "#FFF").css('height', '34.4px').css('width', '100%').parent().append(i);
    $(this).parent().children('.mdi-window-close').hide();
    //监听值的变化
    $(this).on('hide', function () {
        if ($(this).val() != '') {
            $(this).parent().children('.mdi-window-close').show();
        }
    })
    //监听点击事件
    $(this).parent().children('.mdi-window-close').click(function () {
        $(this).hide();
        $(this).parent().children('input').val('');
    });
};
/**
 * 设置右边点击图片
 */
$.fn.inputRightIcon = function (options, callback) {
    var icon = options.icon;
    var id = options.id == undefined ? "" : options.id;
    var remove = options.remove == undefined ? 0 : options.remove;
    var iconLen = $(this).siblings(".form-control-feedback").length * 30;
    //有过一个就不在添加了
    if ($(this).siblings("#" + id).length > 0) {
        return;
    }

    $(this).siblings(".form-control-feedback").each(function () {
        $(this).animate({
            marginRight: iconLen
        });
        iconLen--;
    });

    var padddingRight = $(this).siblings(".form-control-feedback").length

    //设置图片
    var i = '<i class="form-control-feedback ' + icon + '" id="' + id + '" style="margin-right: ' + padddingRight + 'px;pointer-events:auto;cursor:pointer;" data-bv-icon-for="' + $(this).prop('id') + '"></i>';
    $(this).css('background-color', "#FFF").css('height', '34.4px').css('width', '100%').parent().append(i);
    $(this).parent().children('.' + icon.replace(replaceSpace, ".")).click(function () {
        if (remove == 1) {
            $(this).siblings(".form-control-feedback").each(function () {
                var right = ($(this).css("margin-right").replace("px", "") - 30);
                var marginRight = (right < 0 ? 0 : right) + "px";
                $(this).animate({
                    marginRight: marginRight
                })
            });
            $(this).remove();
        }
        callback();
    });
};

/**
 * 搜索选择框
 * @param options
 * @param callback
 */
$.fn.selectInput = function (callback) {
    var $input = $(this);
    //输入框才添加选择按钮
    if ($input.is("input")) {
        var $parent = $input.parent();
        var isLabel = $input.siblings('label').length > 0;
        var searchBtnId = uuid();
        var searchDivId = uuid();
        var searchBtn = '#' + searchBtnId;
        var searchDiv = '#' + searchDivId;
        var searchBtnHtml = '<div  id="' + searchDivId + '" style="position: absolute;text-align: right;top:' + (isLabel ? 25 : 0) + 'px;width: 100%"><button id="' + searchBtnId + '" class="btn btn-default">选择</button></div>';
        $parent.append(searchBtnHtml);
        $input.css('background-color', '#fff');

        $(searchBtn).on('click', function (e) {
            callback();
            e.stopPropagation();
        });
        $(searchDiv).on('click', function () {
            callback();
        });

    }
};

/**
 * 打包图片预览图
 */
function packFilePreviewImg(url, imgName) {
    return '<img src="' + url + '" class="file-preview-image kv-preview-data" title="' + imgName + '" alt="' + imgName + '" style="width:auto;height:150px;">';
}

/**
 * 打包form参数
 * @param $form
 * @returns {{}}
 */
function packFormParams($form) {
    var params = {};
    var formData = $form.serializeArray();
    formData.forEach(function (e) {
        params[e.name] = e.value;
    });
    return params;
}

//Html编码获取Html转义实体
function htmlEncode(value) {
    return $('<div/>').text(value).html();
}

//Html解码获取Html实体
function htmlDecode(value) {
    return $('<div/>').html(value).text();
}

/**
 * 随机UUID
 * @returns {string}
 */
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

/**
 * 空为true false不为空
 * @param val
 * @returns {boolean}
 */
function isEmpty(val) {
    return typeof  val == 'undefined' || val == undefined || val == null || val == '';
}

/**
 * 格式化CST日期的字串
 * @param strDate
 * @param format
 * @returns {*}
 */
function formatCSTDate(strDate, format) {
    return strDate == "" || strDate == null ? "" : formatDate(new Date(strDate), format);
}

/**
 * 格式化日期
 * @param date
 * @param format
 * @returns {void|XML|string|*|{by}}
 */
function formatDate(date, format) {
    var paddNum = function (num) {
        num += "";
        return num.replace(/^(\d)$/, "0$1");
    }
    //指定格式字符
    var cfg = {
        yyyy: date.getFullYear() //年 : 4位
        , yy: date.getFullYear().toString().substring(2)//年 : 2位
        , M: date.getMonth() + 1  //月 : 如果1位的时候不补0
        , MM: paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
        , d: date.getDate()   //日 : 如果1位的时候不补0
        , dd: paddNum(date.getDate())//日 : 如果1位的时候补0
        , hh: date.getHours().toString().length == 1 ? "0" + date.getHours() : date.getHours()  //时
        , mm: date.getMinutes().toString().length == 1 ? "0" + date.getMinutes() : date.getMinutes() //分
        , ss: date.getSeconds().toString().length == 1 ? "0" + date.getSeconds() : date.getSeconds() //秒
    }
    format || (format = "yyyy-MM-dd hh:mm:ss");
    return format.replace(/([a-z])(\1)*/ig, function (m) {
        return cfg[m];
    });
}

/**
 * 格式化时间戳
 * @param jsonDate
 * @returns {Date}
 */
function toDate(date) {
    alert(date);
    var date = new Date(parsedIntDate);  // parsedIntDate passed to date constructor
    return date;
}


/**
 * 返回文件大小
 * @param field_id
 * @returns {number}
 */
function findSize(field_id) {
    var byteSize = $("#" + field_id)[0].files[0].size;
    return (Math.ceil(byteSize / 1024)); // Size returned in KB.
}

/**
 * 返回文件后缀
 * @param file_name
 * @returns {Array|{index: number, input: string}}
 */
function suffix(file_name) {
    var result = /\.[^\.]+/.exec(file_name);
    return result;
}

/**
 * 根据IS_STATUS来获取状态
 * @param IS_STATUS
 * @returns {*}
 */
function getStatusName(IS_STATUS) {
    if (IS_STATUS == STATUS_SUCCESS) {
        return "开启";
    } else if (IS_STATUS == STATUS_ERROR) {
        return "关闭";
    } else {
        return "未知";
    }
}

/**
 * 根据状态设置菜单按钮
 * @param cell
 * @param from 根据的文字
 * @param to 设置的位置
 */
function setFrozenMenu(cell, from, to) {
    if ($(cell).siblings("td").eq(from).text() == STATUS_NORMAL) {
        $(cell).children().eq(to).html('<i class="fa fa-lock"></i>' + NORMAL_TEXT);
    } else {
        $(cell).children().eq(to).html('<i class="fa fa-lock"></i>' + FROZEN_TEXT);
    }
}

/**
 * 根据状态设置菜单文字
 * @param cell
 */
function setFrozenText(cell) {
    $(cell).text(getStatusType($(cell).text()))
}


/**
 * 是不是PC
 * @returns {boolean}
 * @constructor
 */
function isPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
        "SymbianOS", "Windows Phone",
        "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

function getUrl() {
    return window.location.href;
}

// Returns a function, that, as long as it continues to be invoked, will not
// be triggered. The function will be called after it stops being called for
// N milliseconds. If `immediate` is passed, trigger the function on the
// leading edge, instead of the trailing.
/**
 * 节流方法
 * @param func 方法
 * @param wait 等待时间 毫秒
 * @param immediate 是否立即执行
 * @returns {Function}
 */
function debounce(func, wait, immediate) {
    var timeout;
    return function () {
        var context = this, args = arguments;
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            timeout = null;
            if (!immediate) func.apply(context, args);
        }, wait);
        if (immediate && !timeout) func.apply(context, args);
    };
}

/**
 * 方法多少秒内只能调用一次
 * @param func 方法
 * @param duration 时间 以秒计
 * @returns {Function}
 */
function throttle(func, duration) {
    var last;
    return function () {
        var now = Date.now()
        if (last && (now - last) < duration * 1e3) {
            try {
                demo.showNotifyOptions({
                    color: ALERT_WARNING,
                    message: '操作过于频繁,请等待' + ((now - last) / 1e3) + '秒后在进行操作!',
                    delay: 1000,
                });
            } catch (e) {
            }
            return;
        }
        last = now
        func.apply(this, arguments)
    }
}

/**
 * 查看函数执行时间
 * @param func
 * @returns {string}
 */
// function test(func) {
//     var start = new Date().getTime();//起始时间
//     func();//执行待测函数
//     var end = new Date().getTime();//接受时间
//     return (end - start) + "ms";//返回函数执行需要时间
// }

