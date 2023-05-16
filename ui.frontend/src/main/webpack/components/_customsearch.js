
import $ from "jquery";

(function() {  

     
            let searchBtn = document.querySelector(".cmp-customsearch__container-btn");
            let displayResults = document.querySelector(".cmp-customsearch__results");
            let showTotalResults = document.querySelector("#searchResult");


            let rootPath = document.querySelector('.cmp-customsearch__container').getAttribute("cmp-customsearch-path");
            let tempFilterPath = document.querySelector('.cmp-customsearch__container').getAttribute("cmp-customsearch-tempPath");
            let searchlimit = $(".cmp-customsearch__container").attr("cmp-customsearch-limit");


            searchBtn.addEventListener('click', showResults );

            $("#search").keypress(function (e) {
                let key = e.which;
                if (key == 13) {
                    e.preventDefault();
                    showResults();
                }
            });


            function showResults()  {

                
                  let fulltext = $(".cmp-customsearch__container-input").val();
                
                  $.ajax({
                    url: "/content/wknd/us/en/home/jcr:content.myservlet.txt",
                    type: "GET",
                    data: "searchContent=" + fulltext + "&rootPath=" +  rootPath + "&tempFilterPath=" + tempFilterPath,
                    async: true,
                    dataType: "json",

                    success: function(response){

                        let numOfResults = parseInt(response.totalresult);
                        let Results = response.results;

                        showTotalResults.innerHTML = `<p> ${numOfResults} Results found. </p>`;
                        displayResults.innerHTML = '';


                        var maxResults = Math.min(searchlimit, Results.length);
                        for (var i = 0; i < maxResults; i++) {
                            var data = Results[i];
                            var resultElement = createResultElement(data);
                            displayResults.appendChild(resultElement);
                        }

                        // Add Load More Button
                        if (numOfResults > searchlimit) {
                            var moreButton = document.createElement("button");
                            moreButton.textContent = "Load More";
                            moreButton.addEventListener("click", showMoreResults);
                            displayResults.appendChild(moreButton);
                          }


                          function showMoreResults() {

                            moreButton.remove();
                           
                          
                            // Display the next 5 results
                            for (var i=searchlimit; i<numOfResults; i++) {

                                var data = Results[i];
                                var resultElement = createResultElement(data);
                                displayResults.appendChild(resultElement);

                            }

                          }

                          function createResultElement(data) {

                            var resultElement = document.createElement("div");
                            resultElement.className = "cmp-customsearch__results--div";
                          
                            var resultHTML = `<ul class="cmp-customsearch__results--ul">
                                                  <li class="cmp-customsearch__results--li">Title: ${data.title}</li>
                                                  <li class="cmp-customsearch__results--li"> Path: <a href="${data.path}.html"> ${data.path}</a></li>
                                                  <li class="cmp-customsearch__results--li">Description: ${data.description}</li>
                                              </ul>`;
                          
                            resultElement.innerHTML = resultHTML;
                          
                            return resultElement;
                          }



                    },
                    error: function (xhr, status) {
                        console.log('ajax error = ' + xhr.statusText);
                        },
                });
                  
            }

  
  }());