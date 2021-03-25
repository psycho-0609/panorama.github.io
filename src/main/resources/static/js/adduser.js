$(document).ready(function (){
    $('.form-group input').focus(function () {
        $(this).closest('.form-group').addClass('not-empty');
    })

    $('.form-group input').blur(function () {
        if($(this).val() === '') {
            $(this).closest('.form-group').removeClass('not-empty');
        }
    })

    $('.form-group textarea').focus(function () {
        $(this).closest('.form-group').addClass('not-empty');
    })

    $('.form-group texetarea').blur(function () {
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

    $('.form-group textarea').map(function (){
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

    var textarea = document.querySelector('textarea');

    textarea.addEventListener('keydown', autosize);

    function autosize(){
        var el = this;
        setTimeout(function(){
            el.style.cssText = 'height:auto; padding: .375rem .75rem';
            // for box-sizing other than "content-box" use:
            // el.style.cssText = '-moz-box-sizing:content-box';
            el.style.cssText = 'height:' + el.scrollHeight + 'px';
        },0);
    }
})


