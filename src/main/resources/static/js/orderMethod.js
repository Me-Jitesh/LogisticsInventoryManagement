$(document).ready(function () {

    //1. hide error section
    $("#orderModeError").hide();
    $("#orderCodeError").hide();
    $("#orderTypeError").hide();
    $("#orderAcptError").hide();
    $("#orderNoteError").hide();

    //2. define  error variable
    var orderModeError = false;
    var orderCodeError = false;
    var orderTypeError = false;
    var orderAcptError = false;
    var orderNoteError = false;

    //3. define  validate function
    function validate_orderMode() {
        var len = $('[name="orderMode"]:checked').length;
        if (len == 0) {
            $("#orderModeError").show();
            $("#orderModeError").html(" * please  choose <b>order mode</b>");
            $("#orderModeError").css('color', 'red');
            orderModeError = false;
        } else {
            $("#orderModeError").hide();
            orderModeError = true;
        }
        return orderModeError;
    }

    function validate_orderCode() {

        //  Pattern Matching using RegEx
        var exp = /^[A-Z0-9\-\s]{2,20}$/

        var val = $("#orderCode").val();

        if (val == '') {
            $("#orderCodeError").show();
            $("#orderCodeError").html(" * <b>order code</b> can't be empty");
            $("#orderCodeError").css('color', 'red');
            orderCodeError = false;
        } else if (!exp.test(val)) {
            $("#orderCodeError").show();
            $("#orderCodeError").html(" * <b>order code</b> must  be 2-20 uppercase letters");
            $("#orderCodeError").css('color', 'red');
            orderCodeError = false;
        } else {
            $("#orderCodeError").hide();
            orderCodeError = true;
        }
        return orderCodeError;
    }

    function validate_orderType() {
        var val = $("#orderType").val();
        if (val == '') {
            $("#orderTypeError").show();
            $("#orderTypeError").html(" * please  select one <b>type</b>");
            $("#orderTypeError").css('color', 'red');
            orderModeError = false;
        } else {
            $("#orderTypeError").hide();
            orderTypeError = true;
        }
        return orderTypeError;
    }

    function validate_orderAcpt() {
        var len = $('[name="orderAcpt"]:checked').length;
        if (len == 0) {
            $("#orderAcptError").show();
            $("#orderAcptError").html("* please choose atleast one option");
            $("#orderAcptError").css('color', 'red');
            orderAcptError = false;
        } else {
            $("#orderAcptError").hide();
            orderAcptError = true;
        }
        return orderAcptError;
    }

    function validate_orderNote() {

        var exp = /^[A-Za-z0-9\s-.*,@:]{2,20}$/;

        var val = $("#orderNote").val();

        if (val == "") {
            $("#orderNoteError").show();
            $("#orderNoteError").html(" * please  enter <b>note</b>");
            $("#orderNoteError").css("color", "red");
            orderNoteError = false;
        } else if (!exp.test(val)) {
            $("#orderNoteError").show();
            $("#orderNoteError").html(" * <b>order note </b> must  be 2-100 letters");
            $("#orderNoteError").css("color", "red");
            orderNoteError = false;
        } else {
            $("#orderNoteError").hide();
            orderNoteError = true;
        }
        return orderNoteError;
    }

    //4. link  with event

    $('[name="orderMode"]').change(function () {
        validate_orderMode();
    });
    $("#orderCode").keyup(function () {
        // $("#orderCode").val($("#orderCode").val().toUpperCase()); //alternative
        $(this).val($(this).val().toUpperCase());
        validate_orderCode();
    });
    $('[name="orderType"]').change(function () {
        validate_orderType();
    });
    $('[name="orderAcpt"]').click(function () {
        validate_orderAcpt();
    });

    $("#orderNote").keyup(function () {
        validate_orderNote();
    });

    //5. on click form submit
    $("#OrderMetrhodForm").submit(function () {
        validate_orderMode();
        validate_orderCode();
        validate_orderType();
        validate_orderAcpt();
        validate_orderNote();

        if (orderModeError && orderCodeError && orderTypeError && orderAcptError && orderNoteError) {
            return true;
        } else {
            return false;
        }
    });
});