$(function () {
    $('.cate-bar-title').click(function () {
        if ($(this).find('span i').hasClass('fa-angle-down')) {
            $(this).closest('.cate-bar').find('.link-header').slideUp('1000');
            $(this).find('span i').removeClass('fa-angle-down');
            $(this).find('span i').addClass('fa-angle-right');
        }
        else {
            $('.cate-bar-title').closest('.cate-bar').find('.link-header').slideUp('1000');
            $('.cate-bar-title').find('span i').removeClass('fa-angle-down');
            $('.cate-bar-title').find('span i').addClass('fa-angle-right');

            $(this).find('span i').removeClass('fa-angle-right');
            $(this).find('span i').addClass('fa-angle-down');
            $(this).closest('.cate-bar').find('.link-header').slideDown('1000');
            $(this).closest('.cate-bar').find('.link-header').css('display', 'flex');
        }
    })

    $('.hamburger').click(function(e) {
        e.preventDefault();
        $('.side-bar').toggle('1000');
    })
})