<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/10/27
  Time: 11:48
  To change this template use File | Settings | File Templates.
  文件附件上传的js类
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Random" %>
<%
    //重复调用导致id冲突
    Random ran = new Random(System.currentTimeMillis());//以系统时间作为随机种子
    int temp = ran.nextInt(Integer.MAX_VALUE);
%>
<%--隐藏参数--%>
<div>
    <input type="hidden" id="tableId<%=temp%>" value="${tableId}">
    <input type="hidden" id="tableName<%=temp%>" value="${fns:AESEncode(tableName)}">
    <input type="hidden" id="typeCode<%=temp%>" value="${fns:AESEncode(typeCode)}">
    <input type="hidden" id="isSee<%=temp%>" value="${fns:AESEncode(isSee)}">
    <input type="hidden" id="percodeDown<%=temp%>" value="${fns:AESEncode(percodeDown)}">
    <input type="hidden" id="percodeDel<%=temp%>" value="${fns:AESEncode(percodeDel)}">
</div>
<%--id是否加密默认加密--%>
<%--<c:if test="${encryptModel ne null && !encryptModel}">${enclosure.id}</c:if><c:if test="${encryptModel eq null || encryptModel eq true}">${fns:AESEncode(enclosure.id)}</c:if>--%>
<script>
    <%--初始化回调显示参数--%>
    var defaultEnclosure<%=temp%> = new Array();
    var defaultEnclosureName<%=temp%> = new Array();

    <c:if test="${enclosureList != null}">
    <c:forEach items="${enclosureList}" var="enclosure" varStatus="status">

    if (getFileTypeByName('${enclosure.originalName}') == FILE_TYPE_IMG) {
        defaultEnclosure<%=temp%>[${status.index}] = mosaicDefaultFileImg('${IMG_URL}<c:if test="${encryptModel ne null && !encryptModel}">${enclosure.id}</c:if><c:if test="${encryptModel eq null || encryptModel eq true}">${fns:AESEncode(enclosure.id)}</c:if>', '${enclosure.originalName}');
    } else if (getFileTypeByName('${enclosure.originalName}') == FILE_TYPE_PDF) {
        defaultEnclosure<%=temp%>[${status.index}] = mosaicDefaultFilePdf(FILE_PREVIEW + '<c:if test="${encryptModel ne null && !encryptModel}">${enclosure.id}</c:if><c:if test="${encryptModel eq null || encryptModel eq true}">${fns:AESEncode(enclosure.id)}</c:if>');
    }
        <%--else if (getFileTypeByName('${enclosure.originalName}') == FILE_TYPE_OFFICE) {--%>
        <%--defaultEnclosure<%=temp%>[${status.index}] = mosaicDefaultFileOffice(window.location.protocol + "//" + window.location.host + FILE_PREVIEW + '${enclosure.id}');--%>
        <%--} --%>
    else {
        defaultEnclosure<%=temp%>[${status.index}] = DEFAULT_FILE_PREVIEW;
    }

    defaultEnclosureName<%=temp%>[${status.index}] = {
        caption: '${enclosure.originalName}',
        width: '140px',
        url: FILE_DEL,
        downloadUrl: FILE_DOWN + '<c:if test="${encryptModel ne null && !encryptModel}">${enclosure.id}</c:if><c:if test="${encryptModel eq null || encryptModel eq true}">${fns:AESEncode(enclosure.id)}</c:if>',
        key: '<c:if test="${encryptModel ne null && !encryptModel}">${enclosure.id}</c:if><c:if test="${encryptModel eq null || encryptModel eq true}">${fns:AESEncode(enclosure.id)}</c:if>',
        size: '${enclosure.size}',
        type: getFileTypeByName('${enclosure.originalName}'),
        extra: {title: '${articleTitle}'}
    };

    </c:forEach>
    </c:if>

    <%--单个图片显示模式--%>
    <c:if test="${photoUrl != null}">
    var photoUrl<%=temp%> = '<c:if test="${encryptModel ne null && !encryptModel}">${photoUrl}</c:if><c:if test="${encryptModel eq null || encryptModel eq true}">${fns:AESEncode(photoUrl)}</c:if>';
    defaultEnclosure<%=temp%>[0] = mosaicDefaultFileImg('${IMG_URL}' + photoUrl<%=temp%>, '${photoName}');
    defaultEnclosureName<%=temp%>[0] = {
        caption: '${photoName}',
        width: '160px',
        downloadUrl: FILE_DOWN + photoUrl<%=temp%>,
    };
    </c:if>

    <%--初始化上传js--%>
    var $fileInput<%=temp%>;
    var uploadId<%=temp%> = 'uploadInput';
    <c:if test="${uploadInputId != null}">
    uploadId<%=temp%> = '${uploadInputId}';
    </c:if>

    var syncModel<%=temp%> = ${syncModel eq null ? true : syncModel};
    <c:choose>
    <c:when test="${uploadModel == 'img'}">
    $fileInput<%=temp%> = FileMultipleUpload($("#" + uploadId<%=temp%>), {
        tableId: $('#tableId<%=temp%>').val(),
        tableName: $('#tableName<%=temp%>').val(),
        typeCode: $('#typeCode<%=temp%>').val(),
        isSee: $('#isSee<%=temp%>').val(),
        percodeDown: $('#percodeDown<%=temp%>').val(),
        percodeDel: $('#percodeDel<%=temp%>').val(),
    }, syncModel<%=temp%>, defaultEnclosure<%=temp%>, defaultEnclosureName<%=temp%>);
    </c:when>
    <c:otherwise>
    $fileInput<%=temp%> = FileAllUpload($("#" + uploadId<%=temp%>), {
        tableId: $('#tableId<%=temp%>').val(),
        tableName: $('#tableName<%=temp%>').val(),
        typeCode: $('#typeCode<%=temp%>').val(),
        isSee: $('#isSee<%=temp%>').val(),
        percodeDown: $('#percodeDown<%=temp%>').val(),
        percodeDel: $('#percodeDel<%=temp%>').val(),
    }, syncModel<%=temp%>, defaultEnclosure<%=temp%>, defaultEnclosureName<%=temp%>);
    </c:otherwise>
    </c:choose>

    <%--删除按钮设置--%>
    <c:choose>
    <c:when test="${deleteModel == 'false'}">
    <%--没有删除URL就移除删除按钮--%>
    $('#' + uploadId<%=temp%>).parents('.input-group.file-caption-main').siblings(".file-preview").find('.kv-file-remove.btn.btn-kv.btn-default.btn-outline-secondary').each(function () {
        $(this).remove();
    });
    </c:when>
    <c:otherwise>
    $fileInput<%=temp%>.on('filebeforedelete', function () {
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
    })
    </c:otherwise>
    </c:choose>
    <%--预览模式 删除上传框--%>
    <c:if test="${previewModel !=null && previewModel == true}">
    $('.input-group.file-caption-main').remove();
    $('.file-input.file-input-ajax-new .close.fileinput-remove').remove();
    </c:if>
    <%--解决多层模态框关闭导致下一层不能滚动--%>
    $('#kvFileinputModal').on('hidden.bs.modal', function () {
        if ($('.model-custom').length > 0) {
            $(document.body).addClass("modal-open");
        }
    });
</script>