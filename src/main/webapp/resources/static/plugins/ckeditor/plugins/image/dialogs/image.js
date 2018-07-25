function uplaodImage() {
    CKEDITOR.instances.editor.on('change', function (e) {
        var a = e.editor.document;
        var b = a.find("img");
        var count = b.count();
        for (var i = 0; i < count; i++) {
            var src = b.getItem(i).$.src;//获取img的src
            if (src.substring(0, 4) == 'data') {
                var url = 后台请求路径;
                $.ajax({
                    type: "POST",
                    url: url,
                    async: false,//同步，因为修改编辑器内容的时候会多次调用change方法，所以要同步，否则会多次调用后台
                    data: {'src': src},
                    success: function (json) {
                        var data = eval("(" + json + ")");
                        if (data.success) {
                            b.getItem(i).$.src = data.url;
                        }
                    }
                });
            }
        }
    });
}

 