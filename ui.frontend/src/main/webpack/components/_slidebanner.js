import $ from "jquery";
import Swiper, { Navigation, Autoplay, Pagination } from 'swiper';

(function() {
    "use strict";
    var selectors = {
        self:      '[data-cmp-is="cmp-slidebanner"]'
    };

    function Slidebanner(config) {
        function init(config) {
            config.removeAttribute("data-cmp-is");
            const slider = config.querySelector('.swiper-wrapper');

            let horizontalTitle = $(".cmp-slidebanner__container__right--title").attr("cmp-slidebanner-title");
            $(".horizontal-title").text(horizontalTitle);

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
      }

        if (config && config.element) {
            init(config.element);
        }
    }
   
    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new Slidebanner({ element: elements[i] });
        }

        var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
        var body             = document.querySelector("body");
        var observer         = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                var nodesArray = [].slice.call(mutation.addedNodes);
                if (nodesArray.length > 0) {
                    nodesArray.forEach(function(addedNode) {
                        if (addedNode.querySelectorAll) {
                            var elementsArray = [].slice.call(addedNode.querySelectorAll(selectors.self));
                            elementsArray.forEach(function(element) {
                                new Slidebanner({ element: element });
                            });
                        }
                    });
                }
            });
        });

        observer.observe(body, {
            subtree: true,
            childList: true,
            characterData: true
        });
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }
}());