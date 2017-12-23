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

//实时检测用户输入
function checkRtNid(that) {
    var str = $(that).val();
    var last_str = str.charAt(str.length - 1);
    if (str.length === 18 && (last_str === "X" || last_str === "x")) {
        $(that).val(str.substring(0, 17) + "X");
        return;
    }
    if (!$.isNumeric(last_str)) {
        if (str.length > 1) {
            $(that).val(str.substring(0, str.length - 1));
        } else {
            $(that).val("");
        }

    } else if (str.length > 18) {
        $(that).val(str.substring(0, 18));
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