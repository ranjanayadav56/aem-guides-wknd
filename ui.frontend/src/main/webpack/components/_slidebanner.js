import $ from "jquery";
import Swiper, { Navigation, Autoplay, Pagination } from 'swiper';

const slider = document.querySelector('.swiper-wrapper');
if (slider) {
      const swiper = new Swiper('.swiper', {      
            modules: [Navigation, Autoplay, Pagination],
            slidesPerView: 1,
            loop: true,
            pagination: {
                  el: '.swiper-pagination',
                  type: "fraction",
                  renderFraction: function (currentClass) {  
                        return '<span class="' + currentClass + '"></span>';
                  }
            },
            navigation: {
                  nextEl: '.swiper-button-next',
                  prevEl: '.swiper-button-prev',
            },
            autoplay: {
                  delay: 5000,
            }, 
      });
}


let horizontalTitle = $(".cmp-slidebanner__container__right--title").attr("cmp-slidebanner-title");

$(".horizontal-title").text(horizontalTitle);

