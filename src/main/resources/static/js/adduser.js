$(document).ready(function (){
    $('.form-group input').focus(function () {
        $(this).closest('.form-group').addClass('not-empty');
    })

    $('.form-group input').blur(function () {
        if($(this).val() === '') {
            $(this).closest('.form-group').removeClass('not-empty');
        }
    })

    $('input[type=date]').blur(function() {
        if($(this).val() !== '') {
            $(this).css('color', '#ff8d00');
        }
    })
    $('.form-group input').map(function (){
        if($(this).val() != null && $(this).val() != ""){
            $(this).closest('.form-group').addClass('not-empty');
            $(this).css('color','#ff8d00');
        }else{
            $(this).closest('.form-group').removeClass('not-empty');
        }

    })
    $('.form-group select').map(function (){
        if($(this).val() != null){
            $(this).closest('.form-group').addClass('not-empty');
            $(this).css('color','#ff8d00');
        }else{
            $(this).closest('.form-group').removeClass('not-empty');
        }

    })
})


