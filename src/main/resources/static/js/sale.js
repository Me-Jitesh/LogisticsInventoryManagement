$(document).ready(function () {

    //1. hide error section
    $("#saleCodeError").hide();
    $("#refNumberError").hide();
    $("#shipCodeError").hide();
    $("#customerError").hide();
    $("#stockModeError").hide();
    $("#stockSourceError").hide();
    $("#soDescError").hide();

    //2. define  error variable
    let saleCodeError = false;
    let refNumberError = false;
    let shipCodeError = false;
    let customerError = false;
    let stockModeError = false;
    let stockSourceError = false;
    let soDescError = false;

    function validate_saleCode() {

        //  Pattern Matching using RegEx
        let exp = /^[A-Z0-9\-\s]{2,20}$/;

        let val = $("#saleCode").val();

        if (val == "") {
            $("#saleCodeError").show();
            $("#saleCodeError").html(" * required");
            $("#saleCodeError").css("color", "red");
            saleCodeError = false;
        } else if (!exp.test(val)) {
            $("#saleCodeError").show();
            $("#saleCodeError").html(" * <b> code</b> must  be 2-20 uppercase letters");
            $("#saleCodeError").css("color", "red");
            saleCodeError = false;
        } else {
            let id = 0; // for register page
            if ($("#id").val() != undefined) {
                id = $("#id").val(); // for edit page
            }
            $.ajax({
                url: "validatecode", data: {code: val, id: id}, success(resText) {
                    if (resText != "") {
                        $("#saleCodeError").show();
                        $("#saleCodeError").html(resText);
                        $("#saleCodeError").css("color", "red");
                        saleCodeError = false;
                    } else {
                        $("#saleCodeError").hide();
                        saleCodeError = true;
                    }
                },
            });
        }
        return saleCodeError;
    }

    function validate_refNumber() {
        let val = $("#refNumber").val();
        if (val == '') {
            $("#refNumberError").show();
            $("#refNumberError").html(" * required");
            $("#refNumberError").css("color", "red");
            refNumberError = false;
        } else {
            $("#refNumberError").hide();
            refNumberError = true;
        }
        return refNumberError;
    }

    function validate_shipCode() {
        let val = $("#shipCode").val();
        if (val == '') {
            $("#shipCodeError").show();
            $("#shipCodeError").html("* required");
            $("#shipCodeError").css('color', 'red');
            shipCodeError = false;
        } else {
            $("#shipCodeError").hide();
            shipCodeError = true;
        }
        return shipCodeError;
    }

    function validate_customer() {
        let val = $("#customer").val();
        if (val == '') {
            $("#customerError").show();
            $("#customerError").html("* required");
            $("#customerError").css('color', 'red');
            customerError = false;
        } else {
            $("#customerError").hide();
            customerError = true;
        }
        return customerError;
    }

    function validate_stockMode() {
        let len = $('[name="stockMode"]:checked').length;
        if (len == 0) {
            $("#stockModeError").show();
            $("#stockModeError").html("* required");
            $("#stockModeError").css('color', 'red');
            stockModeError = false;
        } else {
            $("#stockModeError").hide();
            stockModeError = true;
        }
        return stockModeError;
    }

    function validate_stockSource() {
        let val = $("#stockSource").val();
        if (val == '') {
            $("#stockSourceError").show();
            $("#stockSourceError").html("* required");
            $("#stockSourceError").css('color', 'red');
            stockSourceError = false;
        } else {
            $("#stockSourceError").hide();
            stockSourceError = true;
        }
        return stockSourceError;
    }

    function validate_soDesc() {
        let exp = /^[A-Za-z0-9\s-.*,@:]{2,100}$/;
        let val = $("#soDesc").val();

        if (val == "") {
            $("#soDescError").show();
            $("#soDescError").html(" * required");
            $("#soDescError").css("color", "red");
            soDescError = false;
        } else if (!exp.test(val)) {
            $("#soDescError").show();
            $("#soDescError").html(" * <b> desc </b> must  be 2-100 letters");
            $("#soDescError").css("color", "red");
            soDescError = false;
        } else {
            $("#soDescError").hide();
            soDescError = true;
        }
        return soDescError;
    }

    //4. link  with event
    $("#saleCode").keyup(function () {
        $(this).val($(this).val().toUpperCase());
        validate_saleCode();
    });

    $("#refNumber").keyup(function () {
        $(this).val($(this).val().toUpperCase());
        validate_refNumber();
    });

    $('[name="st.id"]').change(function () {
        validate_shipCode();
    });

    $('[name="customer.id"]').change(function () {
        validate_customer();
    });

    $('[name="stockMode"]').change(function () {
        validate_stockMode();
    });

    $('[name="stockSource"]').change(function () {
        validate_stockSource();
    });

    $("#soDesc").keyup(function () {
        validate_soDesc();
    });

    //5. on click form submit
    $("#SoForm").submit(function () {
        validate_saleCode();
        validate_refNumber();
        validate_shipCode();
        validate_customer();
        validate_stockMode();
        validate_stockSource();
        validate_soDesc();

        if (saleCodeError && refNumberError && shipCodeError && customerError && stockModeError && stockSourceError && soDescError) {
            return true;
        } else {
            return false;
        }
    });
});
