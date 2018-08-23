/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("darwin", function ($scope, $http) {    
    $scope.familles = [];                            
    $scope.modeles = [];                            
    getFamille();
    getModeles();

    function getFamille() {
        $http({
            method: 'POST',
            url: 'getFamille',
            data: angular.toJson($scope.darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.familles = response.data;
        }, function error(response) {
            console.log(response);
        });
    }
    function getModeles() {
        $http({
            method: 'get',
            url: 'modeles',            
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.modeles = response.data;
        }, function error(response) {
            console.log(response);
        });
    }
    
    $scope.getGenre = function (famille) {
        if (document.getElementById(famille).checked) {
            $.ajax({
                url: 'getGenre?famille=' + famille,
                dataType: 'json',
                success: function (json) {
                    var option = "";
                    for (var i = 0; i < json.length; i++) {
                        option = option + "<div class=\"row\">";
                        option = option + "<li>" + '<input name="value[]" type="checkbox" onclick="getEspece(\'' + famille + '\',\'' + json[i] + '\')" value="typeGenre-famille-' + famille + '-genre-' + json[i] + '" id="typeGenre-famille-' + famille + '-genre-' + json[i] + '" >';
                        option = option + json[i];
                        option = option + "</li></div>";
                        option = option + "<div><ul style=\"list-style-type:none;\" id=\"espece-" + json[i] + "\"></ul></div></div>";
                    }
                    $("#genre-" + famille).append(option).trigger('change');
                }
            });
        } else {
            var option = "";
            $("#genre-" + famille).html('<ul id="genre-{{famille}}"></ul>');
        }
    };
    
    
    $scope.changeLastUrlOverlay = function (url) {
        addOverlay(url);        
    };
    
});

