'use strict';

//身份证号校验
function checkNid(that) {
    if ($(that).val().length !== 18) {
        that.focus();
        mdui.snackbar({
            message: "身份证号不是18位,当前" + $(that).val().length + "位.请检查!"
        });
    }
}

//提交检查
function submit_staff() {
    var $comeDateId = $('#comeDate_id');
    var $startDateId = $('#startDate_id');
    if ($('#comeDate').val() === "") {
        $comeDateId.addClass("mdui-textfield-invalid");
        mdui.snackbar({
            message: "请选择来校日期!"
        });
    } else {
        $comeDateId.removeClass("mdui-textfield-invalid");
    }
    if ($('#startDate').val() === "") {
        $startDateId.addClass("mdui-textfield-invalid");
        mdui.snackbar({
            message: "请选择工龄起始日期!"
        });
    } else {
        $startDateId.removeClass("mdui-textfield-invalid");
    }
}