<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form role="form" enctype="multipart/form-data" method="post" id="uploadImg" name="uploadImg">
    <div class="embed-responsive embed-responsive-16by9">
        <div class="embed-responsive-item pre-scrollable">
            <img alt="" src="${activeUser.imgpath}" id="cut-img"
                 class="img-responsive img-thumbnail"/>
        </div>
    </div>
    <div class="white-divider-md"></div>
    <input type="file" name="fileUpload" id="fileUpload"/>
    <div class="white-divider-md"></div>
    <div id="alert" class="alert alert-danger hidden" role="alert"></div>
    <input type="hidden" id="x" name="x"/>
    <input type="hidden" id="y" name="y"/>
    <input type="hidden" id="w" name="w"/>
    <input type="hidden" id="h" name="h"/>
    <input type="hidden" id="sw" name="sw"/>
    <input type="hidden" id="sh" name="sh"/>
    <input type="hidden" id="userId" name="userId" value="${activeUser.userid}">
</form>

<script>
    $(document).ready(function () {
        new PageInit().init();
    })
    //初始化文件上传
    new PageInit().init();

    //裁剪图片
    function PageInit() {
        var api = null;
        var _this = this;
        this.init = function () {
            this.portraitUpload();
        };

        this.portraitUpload = function () {
            var fileUp = new FileUpload();
            var portrait = $('#fileUpload');
            var alert = $('#alert');
            fileUp.portrait(portrait, BASE_URL + 'uploadHeadPortrait.action', _this.getExtraData);
            portrait.on('change', _this.readURL);
            portrait.on('fileuploaderror', function (event, data, msg) {
                alert.removeClass('hidden').html(msg);
                fileUp.fileinput('disable');
                showError("上传图片失败请重试!");
            });
            portrait.on('fileclear', function (event) {
                alert.addClass('hidden').html();
            });
            portrait.on('fileloaded', function (event, file, previewId, index, reader) {
                alert.addClass('hidden').html();
            });
            portrait.on('fileuploaded', function (event, data) {
                if (!data.response.status) {
                    showSuccess(data.response.message);
                    $(".headportrait").each(function () {
                        $(this).prop("src", '${activeUser.imgpath}');
                    });
                    hidderEditActiveUserImgModal();
                } else {
                    demo.showNotify(ALERT_WARNING,data.message);
                }
            })
        };

        this.readURL = function () {
            var img = $('#cut-img');
            var input = $('#fileUpload');
            if (input[0].files && input[0].files[0]) {
                var reader = new FileReader();
                reader.readAsDataURL(input[0].files[0]);
                reader.onload = function (e) {
                    //重新加载
                    img.parent().html(' <img id="cut-img" class="img-responsive img-thumbnail"/>');
                    img = $('#cut-img');
//                    img.removeAttr('src');
                    img.prop('src', e.target.result);

                    img.Jcrop({
                        setSelect: [20, 20, 200, 200],
                        handleSize: 10,
                        aspectRatio: 1,
                        onSelect: updateCords
                    }, function () {
                        api = this;
                    });
                };
                if (api != undefined) {
                    api.destroy();
                }
            }

            function updateCords(obj) {
                $("#x").val(obj.x);
                $("#y").val(obj.y);
                $("#w").val(obj.w);
                $("#h").val(obj.h);
            }
        };

        this.getExtraData = function () {
            return {
                sw: $('.jcrop-holder').css('width'),
                sh: $('.jcrop-holder').css('height'),
                x: $('#x').val(),
                y: $('#y').val(),
                w: $('#w').val(),
                h: $('#h').val(),
                userId: $("#userId").val()
            }
        }
    }

    // 文件上传
    function FileUpload() {
        this.portrait = function (target, uploadUrl, data) {
            target.fileinput({
                language: 'zh', //设置语言
                maxFileSize: 4096,//文件最大容量
                uploadExtraData: data,//上传时除了文件以外的其他额外数据
                showPreview: false,//隐藏预览
                uploadAsync: true,//ajax同步
                dropZoneEnabled: false,//是否显示拖拽区域
                uploadUrl: uploadUrl, //上传的地址
                allowedFileExtensions: ['jpg', 'png', 'jpeg'],//接收的文件后缀
                showUpload: false, //是否显示上传按钮
                showCaption: true,//是否显示标题
                browseClass: "btn btn-primary", //按钮样式
                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            });
        }
    }
</script>