<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2018/5/22
  Time: 23:48
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <div class="box" id="dataGridBox">
                <div class="box-header">
                    <div class="col-md-12 btn-group-header">
                        <s:button back="false"></s:button>
                    </div>
                </div>
                <div class="box-body">
                    <div class="col-md-12">
                        <div class="box box-default">
                            <div class="box-body">
                                <button id="validate" class="btn btn-warning">验证</button>
                                <s:fileInput title="上传文件测试" sdtCode="FILE_TEST" tableId="123456" tableName="SYS_FILE" typeCode="file" allowFile="true"></s:fileInput>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<script>
    $('#validate').on('click',function () {
       var result =  file.validate();
       if(result.flag == false){
           demo.showNotify(ALERT_WARNING, result.message);
       }
    });
</script>