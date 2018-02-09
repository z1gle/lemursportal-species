/*﻿ 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("controller", function ($scope, $http) {
    $scope.liste = [];
    $scope.object = {};
    $scope.recherche = "";
    getall();
    function getall() {
        $http({
            method: 'POST',
            url: 'getFamille',
            data: angular.toJson($scope.darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
        }, function error(response) {
            console.log(response);
        });
    }

    $scope.rechercher = function () {
        $http({
            method: 'POST',
            url: 'findByespeceDwc',
            data: angular.toJson($scope.darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            $scope.recherche = $scope.darwin.scientificname;
        }, function error(response) {
            console.log(response.statusText);
        });
    };
    $scope.rechercherMulti = function () {
        var formData = {
            'validation': parseInt($('select[name=validation]').val()),
            'chercheur': $('input[name=chercheur]').val()
        };
        $http({
            method: 'POST',
            url: 'findByespeceDwcs',
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
//            dataType: 'json'
        }).then(function success(response) {
            $scope.liste = response.data;
//            $scope.recherche=$scope.darwin.scientificname;
        }, function error(response) {
            console.log(response.statusText);
        });
    };
    $scope.getGenre = function (famille) {
        if ($("#" + famille).is(":checked")) {
            $.ajax({
                url: 'getGenre?famille=' + famille,
                dataType: 'json',
                success: function (json) {
                    var option = "";
                    for (var i = 0; i < json.length; i++) {
                        option = option + "<div class=\"row\"><div class=\"col-md-6\">";
                        option = option + "<li>" + '<input name="valeur[]" type="checkbox" onclick="getEspece(\''+famille+'\',\''+json[i]+'\')" value="typeGenre-famille-' + famille + '-genre-' + json[i] + '" id="typeGenre-famille-' + famille + '-genre-' + json[i] + '" >';
                        option = option + json[i];
                        option = option + "</li></div>";
                        option = option + "<div class=\"col-md-6\"><ul id=\"espece-"+json[i]+"\"></ul></div></div>";
                    }
                    $("#genre-" + famille).append(option).trigger('change');
                    $("#titreGenre").show();
                }
            });
        } else {
            var option = "";
            $("#genre-" + famille).html('<ul id="genre-{{famille}}"></ul>');
//            $("#titreGenre").hide();
        }
    };
    
//    $scope.getEspece = function (famille, genre) {
//        if ($("#" + genre).is(":checked")) {
//            $.ajax({
//                url: 'getEspece?genre=' + genre,
//                dataType: 'json',
//                success: function (json) {
//                    var option = "";
//                    for (var i = 0; i < json.length; i++) {
//                        option = option + "<li>" + '<input type="checkbox" value="typeEspece-famille-' + famille + '-genre-' + genre + '-espece-"'+json[i]+' >';
//                        option = option + json[i];
//                        option = option + "</li>";
//                    }
//                    $("#espece-" + genre).append(option).trigger('change');
//                }
//            });
//        } else {
//            var option = "";
//            $("#espece-" + genre).html('<ul id="genre-{{famille}}"></ul>');
//        }
//    };


//    $(document).ready(function () {
//        $('#form-search').submit(function (event) {
//            // get the form data
//            var formData = {
//                'validation': $('select[name=validation]').val(),
//                'chercheur': $('input[name=chercheur]').val()
//
//            };
//            // process the form
//            $.ajax({
//                type: 'POST',
//                url: 'findByespeceDwcs',
//                data: formData,
//                dataType: 'json',
//                success: function (data) {
//                    $scope.liste = data.data;
//                }
//            });
//            // stop the form from submitting and refreshing
//            event.preventDefault();
//        });
//    });


    $scope.editer = function (darwin) {
        $("#editOrnew").modal({backdrop: 'static'});
        $scope.form = angular.copy(darwin);
    };

    $scope.annuler = function () {
        $("#editOrnew").modal("hide");
        $scope.form = {};
    };

    $scope.save = function () {
        $http({
            method: 'POST',
            url: 'saveDwc',
            data: angular.toJson($scope.form),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.form = {};
            $("#editOrnew").modal("hide");
            getall();
        }, function error(response) {
            console.log(response.statusText);
        });
    };

    $scope.delete = function (darwin) {
        $http({
            method: 'POST',
            url: 'deleteDwc',
            data: angular.toJson(darwin),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            getall();
        }, function error(response) {
            console.log(response.statusText);
        });
    };
});

