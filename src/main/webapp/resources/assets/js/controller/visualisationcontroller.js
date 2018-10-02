/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("darwin", function ($scope, $http) {
    $scope.familles = [];
    $scope.modeles = [];
    $scope.modelesSorted = [];
    $scope.shp = [];
    getFamille();
    getModeles();
    getShp();
    $scope.filtreModele;

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
    function getShp() {
        $http({
            method: 'GET',
            url: 'shapefiles',
            data: angular.toJson($scope.darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.shp = response.data;
        }, function error(response) {
            console.log(response);
        });
    }
    function getModeles() {
        $http({
            method: 'get',
            url: 'modeles?limite=-1',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.modeles = response.data;
            $scope.modelesSorted = response.data;
        }, function error(response) {
            console.log(response);
        });
    }

    function fillKml(mark, id, name) {
        $('.kml' + id).remove();
        var body = '';
        for (var i = 0; i < mark.length; i++) {
            var row = '<li class="kml' + id + '">';
            row += '<input id="' + name + '!!' + mark[i].gid + '" type="checkbox"  name="gids-kml">  ' + mark[i].name;
            row += '</li>';
            body += row;
        }
        $('#kml-' + name).append(body);
    }

    $scope.getKmlFromShp = function (id, name) {
        if ($('#shp-' + id)[0].checked == true) {
            $http({
                method: 'GET',
                url: 'kmls?idShp=' + id,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                fillKml(response.data, id, name);
            });
        } else {
            $('.kml' + id).remove();
        }
    };

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

    $scope.sort = function () {
       console.log("sort");
       $scope.modelesSorted = [];
       for(var i = 0; i < $scope.modeles.length; i++) {
           console.log($scope.filtreModele);
           if($scope.modeles[i].name.indexOf($scope.filtreModele) > -1) {
               $scope.modelesSorted.push($scope.modeles[i]);
           } else if($scope.filtreModele == '') {
               $scope.modelesSorted = $scope.modeles;
           }
       }
       console.log($scope.modelesSorted);
    };

});

