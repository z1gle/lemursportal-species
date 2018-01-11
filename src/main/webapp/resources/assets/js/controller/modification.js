/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("object", function ($scope, $http) {
    $scope.object = {};
    var urlLoad = $('#load').val();
    var urlSet = $('#set').val();
    var urlRedirect = $('#redirection').val();
    $scope.urlSave = "";
    load();

    function load() {
        var formData = {
            'id': $('#idDwc').val()
        };
        $http({
            method: 'POST',
            url: urlLoad,
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.object = response.data;
        }, function error(response) {
            console.log(response);
        });
    }
    $scope.save = function () {
        $http({
            method: 'POST',
            url: urlSet,
            data: angular.toJson($scope.object),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            window.location = urlRedirect;
        }, function error(response) {
            console.log(response.statusText);
        });
    };

//    $scope.rechercher = function () {
//        $http({
//            method: 'POST',
//            url: 'findByespeceDwc',
//            data: angular.toJson($scope.darwin),
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            }
//        }).then(function success(response) {
//            $scope.liste = response.data;
//            $scope.recherche = $scope.darwin.scientificname;
//        }, function error(response) {
//            console.log(response.statusText);
//        });
//    };
//
//    $scope.rechercherMulti=function(){
//        var formData = {
//                'validation': parseInt($('select[name=validation]').val()),
//                'chercheur': $('input[name=chercheur]').val()
//            };
//        $http({
//            method : 'POST',
//            url : 'findByespeceDwcs',
//            data : formData,
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            }
////            dataType: 'json'
//        }).then(function success(response){
//            $scope.liste=response.data;
////            $scope.recherche=$scope.darwin.scientificname;
//        },function error(response){
//            console.log(response.statusText);
//        });
//    };
//
//
////    $(document).ready(function () {
////        $('#form-search').submit(function (event) {
////            // get the form data
////            var formData = {
////                'validation': $('select[name=validation]').val(),
////                'chercheur': $('input[name=chercheur]').val()
////
////            };
////            // process the form
////            $.ajax({
////                type: 'POST',
////                url: 'findByespeceDwcs',
////                data: formData,
////                dataType: 'json',
////                success: function (data) {
////                    $scope.liste = data.data;
////                }
////            });
////            // stop the form from submitting and refreshing
////            event.preventDefault();
////        });
////    });
//
//
//    $scope.editer = function (darwin) {
//        $("#editOrnew").modal({backdrop: 'static'});
//        $scope.form = angular.copy(darwin);
//    };
//
//    $scope.annuler = function () {
//        $("#editOrnew").modal("hide");
//        $scope.form = {};
//    };



//    $scope.delete = function (darwin) {
//        $http({
//            method: 'POST',
//            url: 'deleteDwc',
//            data: angular.toJson(darwin),
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            }
//        }).then(function success(response) {
//            getall();
//        }, function error(response) {
//            console.log(response.statusText);
//        });
//    };
});

