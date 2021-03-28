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
        }
        $('.cate-bar-title').removeClass('active');
        if ($(this).find('span i').hasClass('fa-angle-down')) {
            $(this).addClass('active');
        }
    })

    $('.hamburger').click(function (e) {
        e.preventDefault();
        var effect = 'slide';
        var duration = 1000;

        $('.side-bar').toggle(effect, 'left', duration);
        // $('.side-bar-content').toggle(effect, 'left', duration);
        $('.container').toggleClass('non-pl');
        $('.container-page').toggleClass('non-pl');
    })
})