$(document).ready(function () {
    //1. hide error section
    $("#musTypeError").hide();
    $("#musModelError").hide();
    $("#musDescError").hide();
    //2. define  error variable
    var musTypeError = false;
    var musModelError = false;
    var musDescError = false;

    //3. define  validate function
    function validate_musType() {
        var val = $("#musType").val();
        if (val == "") {
            $("#musTypeError").show();
            $("#musTypeError").html(" * Please  select one <b>mus</b>");
            $("#musTypeError").css("color", "red");
            musTypeError = false;
        } else {
            $("#musTypeError").hide();
            musTypeError = true;
        }
        return musTypeError;
    }

    function validate_musModel() {

        //  Pattern Matching using RegEx
        /*
          /^ = Opening
          $/ = Closing
           A-Z = allowing uppercase letters only
           0-9 = allowing numbers
           \- = allowing hyphen symbol
           \s = allowing spaces
           {2-20} = String length should be 2 to 20
           */

        var exp = /^[A-Z0-9\-\s]{2,20}$/;
        var val = $("#musModel").val();
        if (val == "") {
            $("#musModelError").show();
            $("#musModelError").html(" * <b>mus model</b> can't be empty");
            $("#musModelError").css("color", "red");
            musModelError = false;
        } else if (!exp.test(val)) {
            $("#musModelError").show();
            $("#musModelError").html(
                " * <b>mus model </b> must  be 2-20 uppercase letters"
            );
            $("#musModelError").css("color", "red");
            musModelError = false;
        } else {
            var id = 0; // for register page
            if ($("#id").val() != undefined) { // for edit page
                id = $("#id").val();
            }
            $.ajax({
                url: '/mus/validatemodel',
                data: {"musModel": val, "id": id},
                success(resText) {
                    if (resText != "") {
                        $("#musModelError").show();
                        $("#musModelError").html(resText);
                        $("#musModelError").css('color', 'red');
                        musModelError = false;
                    } else {
                        $("#musModelError").hide();
                        musModelError = true;
                    }
                }
            });
        }
        return musModelError;
    }

    function validate_musDesc() {
        var exp = /^[A-Za-z0-9\s-.*,@:]{2,20}$/;
        var val = $("#musDesc").val();
        if (val == "") {
            $("#musDescError").show();
            $("#musDescError").html(" * please  enter <b>Description</b>");
            $("#musDescError").css("color", "red");
            musDescError = false;
        } else if (!exp.test(val)) {
            $("#musDescError").show();
            $("#musDescError").html(
                " * <b>mus desc </b> must  be 2-100 letters"
            );
            $("#musDescError").css("color", "red");
            musDescError = false;
        } else {
            $("#musDescError").hide();
            musDescError = true;
        }
        return musDescError;
    }

    //4. link  with event
    $("#musType").change(function () {
        validate_musType();
    });

    $("#musModel").keyup(function () {
        // $("#musModel").val($("#musModel").val().toUpperCase()); //alternative
        $(this).val($(this).val().toUpperCase());
        validate_musModel();
    });

    $("#musDesc").keyup(function () {
        validate_musDesc();
    });

    //5. on click form submit
    $("#MusForm").submit(function () {
        validate_musType();
        validate_musModel();
        validate_musDesc();
        if (musTypeError && musModelError && musDescError) return true;
        else return false;
    });
});