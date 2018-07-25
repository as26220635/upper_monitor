/**** 主界面使用 ****/
$(function () {

});

/**
 * 加载请添加
 * @returns {string}
 */
function addLoadingDiv() {
    var html = '<div class="overlay"> <i class="fa fa-refresh fa-spin"></i> </div>';
    return html;
}

function addLoadingContentDiv(div) {
    $(div).append('<div class="overlay"> <i class="fa fa-refresh fa-spin"></i> </div>');
}

function showLoadingContentDiv() {
    $('#boxContentDiv').append('<div class="overlay"> <i class="fa fa-refresh fa-spin"></i> </div>');
}
/**
 * 移除加载
 */
function removeLoadingDiv() {
    $(".overlay").remove();
}

function removeLoadingDivJquery(jquery) {
    $(jquery + " .overlay").remove();
}

//滚动到位置
function animateScrollTop(topObj) {
    $('html,body').animate({
        scrollTop: topObj
    }, 700);
}


function splitOption(options) {
    var settings = $.extend({
        value: '',
        name: '',
        options: ''
    }, options);
    return '<option value="' + settings.value + '" ' + settings.options + '>' + settings.name + '</option>';
}


