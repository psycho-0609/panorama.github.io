$('a[href*="#submit-file-wrapper"]').click(function (event) {
    // On-page links
    if (
        location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '')
        &&
        location.hostname == this.hostname
    ) {
        // Figure out element to scroll to
        var target = $(this.hash);
        target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
        // Does a scroll target exist?
        if (target.length) {
            // Only prevent default if animation is actually gonna happen
            event.preventDefault();
            $('html, body').animate({
                scrollTop: target.offset().top
            }, 1000, function () {
                // Callback after animation
                // Must change focus!
                var $target = $(target);
                $target.focus();
                if ($target.is(":focus")) { // Checking if the target was focused
                    return false;
                } else {
                    $target.attr('tabindex', '-1'); // Adding tabindex for elements not focusable
                    $target.focus(); // Set focus again
                };
            });
        }
    }
});


var inputs = document.querySelectorAll('.inputfile');
Array.prototype.forEach.call(inputs, function (input) {
    // var nameInput = $(input).attr('name'),
    //     label = document.querySelectorAll(nameInput + 'Label');
    // labelVal = label.innerHTML;
    input.addEventListener('change', function (e) {
        label = $(this).closest('.form-upload').find('label');
        labelVal = label.html();
        var fileName = '';
        fileName = e.target.value.split('\\').pop();

        if (fileName) {
            label.text(fileName);
        }
        else {
            label.html(labelVal);
        }
        if($(this).attr('name').includes('Edit')) {
            label.css('background-color', '#5b92e5');
        }
    });
});


(function ($) {
    $.fn.attachmentUploader = function () {
        const uploadControl = $('.img-inputfile');
        $(uploadControl).on('change', function (e) {
            console.log($(this));
            const preview = $(this).closest('.form-upload').children('.form-upload__preview');
            const files = e.target.files;

            function previewUpload(file) {
                if (/\.(jpe?g|png|gif)$/i.test(file.name)) {
                    var reader = new FileReader();
                    reader.addEventListener('load', function () {
                        const html =
                            '<div class=\"form-upload__item\">' +
                            '<div class="form-upload__item-thumbnail" style="background-image: url(' + this.result + ')"></div>' +
                            '<p class="form-upload__item-name">' + file.name + '</p>' +
                            '</div>';
                        preview.empty();
                        preview.append(html);
                    }, false);
                    reader.readAsDataURL(file);
                } else {
                    alert('Please upload image only');
                    uploadControl.val('');
                }
            }

            [].forEach.call(files, previewUpload);
        })
    }
})(jQuery)

$('.form-upload').attachmentUploader();


$('.submit-wrapper span').click(function () {
    $(this).closest('.file-submission-form').find('.cover-hidden-submit-wrapper').removeClass('dp-none');
})

$('.button-edit-article a').click(function (e) {
    e.preventDefault();
    $(this).closest('.rp-show-wrapper').find('.cover-hidden-submit-wrapper').removeClass('dp-none');
})

$('#insert-form-submit').click(function () {
    if(validity('fileTitle', 'Please enter complete data') && validity('fileUpload', 'Please upload a File PDF') && validity('fileImage', 'Please Upload a Image')) {
        $('.cover-hidden-submit-wrapper').addClass('dp-none');
        $('.submit-wrapper span').addClass('dp-none');
        $('.formSubmit').removeClass('dp-none');
        $('#submitFileTitle').html($('#fileTitle').val());
        $('#submitFileUpload').html($('#fileUploadLabel').html());
        $('#submitFileImage').html($('#fileImageLabel').closest('.form-upload').children('.form-upload__preview').html());
        console.log($("#fileUpload").val());
    }
})

$('.hidden-submit-wrapper').hover(function () {
    mouse_is_inside = true;
}, function () {
    mouse_is_inside = false;
});

$(".cover-hidden-submit-wrapper").mouseup(function () {
    if (!mouse_is_inside) $(".cover-hidden-submit-wrapper").addClass('dp-none');
});

$('.cancel-button input').click(function () {
    $('.formSubmit').addClass('dp-none');
    $('.submit-wrapper span').removeClass('dp-none');
    $('#fileUploadLabel p').text('Choose a PDF file');
    $('#fileImageLabel p').text('Choose a Image');
})

$('.submit-button-div input').click(function(e) {
    if(!$('.submit-wrapper span').hasClass('dp-none')) {
        e.preventDefault();
    }
})


function validity(idInput, message) {
    let fieldInput = document.getElementById(idInput);
    console.log(fieldInput)
    let validityState = fieldInput.validity;
    console.log(idInput, validityState);
    if(validityState.valueMissing) {
        alert(message);
        return false;
    }
    else {
        return true;
    }
}