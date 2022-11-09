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
    function validate_mus() {
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
            $("#musModelError").hide();
            musModelError = true;
        }

        return musModelError;
    }

    function validate_musDesc() {
        var val = $("#musDesc").val();
        if (val == "") {
            $("#musDescError").show();
            $("#musDescError").html(" * please  enter <b>Description</b>");
            $("#musDescError").css("color", "red");
            musDescError = false;
        } else {
            $("#musDescError").hide();
            musDescError = true;
        }
        return musDescError;
    }

    //4. link  with event
    $("#mus").change(function () {
        validate_mus();
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
        validate_mus();
        validate_musModel();
        validate_musDesc();
        if (musTypeError && musModelError && musDescError) return true;
        else return false;
    });
});