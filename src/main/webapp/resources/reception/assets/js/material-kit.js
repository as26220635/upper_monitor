/*!

 =========================================================
 * Material Kit - v1.1.1.0
 =========================================================

 * Product Page: http://www.creative-tim.com/product/material-kit
 * Copyright 2017 Creative Tim (http://www.creative-tim.com)
 * Licensed under MIT (https://github.com/timcreative/material-kit/blob/master/LICENSE.md)

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 */

var transparent = true;

var transparentDemo = true;
var fixedTop = false;

var navbar_initialized = false;

$(document).ready(function () {

    // Init Material scripts for buttons ripples, inputs animations etc, more info on the next link https://github.com/FezVrasta/bootstrap-material-design#materialjs
    $.material.init();

    //  Activate the Tooltips
    $('[data-toggle="tooltip"], [rel="tooltip"]').tooltip();

    // Activate Datepicker
    if ($('.datepicker').length != 0) {
        $('.datepicker').datepicker({
            weekStart: 1
        });
    }

    // navbar切换动画，小菜单的时候滚动就关闭菜单
    if ($('.navbar-color-on-scroll').length != 0) {
        $(window).on('scroll', materialKit.checkScrollForTransparentNavbar);
        $(window).on('scroll', materialKit.checkScrollForNavbarMenu);
    }

    // Activate Popovers
    // $('[data-toggle="popover"]').popover();

    //点击菜单的a链接就关闭菜单
    $('#nvabar_menu a[data-pjax]').on('click', function () {
        $('html').removeClass('nav-open');
        materialKit.misc.navbar_menu_visible = 0;
        $('#bodyClick').remove();
        setTimeout(function () {
            $(document).removeClass('toggled');
        }, 550);
        $('html').removeClass('nav-open-absolute');

        $('#rightMenuAffix').fadeIn();

    });

});
//小菜单切换
$(document).on('click', '.navbar-toggle', function () {
    $toggle = $(this);
    if (materialKit.misc.navbar_menu_visible == 1) {
        $('html').removeClass('nav-open');
        materialKit.misc.navbar_menu_visible = 0;
        $('#bodyClick').remove();
        setTimeout(function () {
            $toggle.removeClass('toggled')
        }, 550);
        $('html').removeClass('nav-open-absolute');

        $('#rightMenuAffix').fadeIn();
    } else {
        setTimeout(function () {
            $toggle.addClass('toggled')
        }, 580);
        div = '<div id="bodyClick"></div>';
        $(div).appendTo("body").click(function () {
            $('html').removeClass('nav-open');
            if ($('nav').hasClass('navbar-absolute')) {
                $('html').removeClass('nav-open-absolute')
            }
            materialKit.misc.navbar_menu_visible = 0;
            $('#bodyClick').remove();
            setTimeout(function () {
                $toggle.removeClass('toggled')
            }, 550)

            $('#rightMenuAffix').fadeIn();
        });
        if ($('nav').hasClass('navbar-absolute')) {
            $('html').addClass('nav-open-absolute')
        }
        $('html').addClass('nav-open');

        $('#rightMenuAffix').fadeOut();

        materialKit.misc.navbar_menu_visible = 1
    }
});

materialKit = {
    misc: {
        navbar_menu_visible: 0
    },

    checkScrollForTransparentNavbar: debounce(function () {
        if ($(document).scrollTop() > 260) {
            if (transparent) {
                transparent = false;
                $('.navbar-color-on-scroll').removeClass('navbar-transparent');
            }
        } else {
            if (!transparent) {
                transparent = true;
                $('.navbar-color-on-scroll').addClass('navbar-transparent');

                if ($(".header-out").length > 0) {
                    $('#navbarHeader1').removeClass("header-out");
                    $('#navbarHeader2').removeClass("header-up");
                }
            }
        }
    }, 17),
    checkScrollForNavbarMenu: debounce(function () {
        if (materialKit.misc.navbar_menu_visible == 1) {
            $('html').removeClass('nav-open');
            materialKit.misc.navbar_menu_visible = 0;
            $('#bodyClick').remove();
            setTimeout(function () {
                $toggle.removeClass('toggled')
            }, 550);
            $('html').removeClass('nav-open-absolute')
            $('#rightMenuAffix').fadeIn();
        }
    }, 50),
    initSliders: function () {
        // Sliders for demo purpose
        $('#sliderRegular').noUiSlider({
            start: 40,
            connect: "lower",
            range: {
                min: 0,
                max: 100
            }
        });

        $('#sliderDouble').noUiSlider({
            start: [20, 60],
            connect: true,
            range: {
                min: 0,
                max: 100
            }
        });
    }
}


var big_image;

materialKitDemo = {
    checkScrollForParallax: debounce(function () {
        var current_scroll = $(this).scrollTop();

        oVal = ($(window).scrollTop() / 3);
        big_image.css({
            'transform': 'translate3d(0,' + oVal + 'px,0)',
            '-webkit-transform': 'translate3d(0,' + oVal + 'px,0)',
            '-ms-transform': 'translate3d(0,' + oVal + 'px,0)',
            '-o-transform': 'translate3d(0,' + oVal + 'px,0)'
        });

    }, 6)

}
// Returns a function, that, as long as it continues to be invoked, will not
// be triggered. The function will be called after it stops being called for
// N milliseconds. If `immediate` is passed, trigger the function on the
// leading edge, instead of the trailing.

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
};
