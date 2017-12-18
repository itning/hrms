'use strict';
$(function () {
    /*夜间模式功能 start*/
    var $nightMode = $('#night_mode');
    if (localStorage.getItem('night_mode') === 'true') {
        $('body').addClass('mdui-theme-layout-dark');
        $nightMode.prop('checked', true);
    }
    $nightMode.click(function () {
        if ($(this).prop('checked')) {
            localStorage.setItem('night_mode', true);
            $('body').addClass('mdui-theme-layout-dark');
        } else {
            localStorage.setItem('night_mode', false);
            $('body').removeClass('mdui-theme-layout-dark');
        }
    });
    /*夜间模式功能 end*/
});