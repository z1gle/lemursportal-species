/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("shapefile", function ($scope, $http) {
    $scope.shapefiles = [];
//    $scope.categories = [];
    $scope.shapefile = {};
    $scope.recherche = "";
    $scope.file = {};
    $scope.pages = [];
    $scope.recherchePagination = 0;
    $scope.pageEnCours = 0;
    $scope.lastPage = 0;
    $scope.total = 0;
    getall();

    function getall() {
        $http({
            method: 'GET',
            url: 'shapefiles',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.shapefiles = response.data;
            $("#loader-spinner").hide();
            $http({
                method: 'GET',
                url: 'shapefiles/total',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                paginer(response.data, 10, 1);
            }, function error(response) {
                console.log(response);
            });
        }, function error(response) {
            console.log(response);
            $("#loader-spinner").hide();
        });
    }

//    function getallCategorie() {
//        $http({
//            method: 'GET',
//            url: 'shpCategories',
//            headers: {
//                'Accept': 'application/json',
//                'Content-Type': 'application/json'
//            }
//        }).then(function success(response) {
//            $scope.categories = response.data;
//        }, function error(response) {
//            console.log(response);
//            $("#loader-spinner").hide();
//        });
//    }
    function paginer(liste, pas, position) {
        $scope.total = liste;
        $scope.pages = [];
        var temp = liste / pas;
        temp = temp.toFixed(0);
        temp = parseInt(temp);
        if (temp * pas < liste)
            temp += 1;
        var index = 0;
        for (var i = 0; i < temp; i++) {
            if (position - 3 <= i && i < position + 3) {
                $scope.pages[index] = i + 1;
                index++;
            }
        }
        $scope.pageEnCours = position;
        $scope.lastPage = temp;
        $('#pageFin').val(temp);
        $('#pageEnCours').val(position);
    }

    $scope.editer = function (id) {
        $('#idShapefile').val(id);
    };
    $scope.ajouter = function () {
        $('#idShapefile').val(-1);
    };
    $scope.del = function (id) {
        $('#idToDelete').val(id);
    };

    $scope.upload = function () {
        var formData = new FormData();
        var file = $('#shapefile-asc')[0].files[0];
        formData.append("shapefile", file);
        formData.append("shapeLabel", $('#newShapefileName').val());
//        formData.append("categorie", $('#newShapefileCategorie').val());
        var meth = 'POST';
        var url = 'shp';
        if ($('#idShapefile').val() != -1) {
            url = 'shapefile/' + $('#idShapefile').val();
            meth = 'PUT';
        }
        console.log($('#idShapefile').val() != -1);
        $http({
            method: 'POST',
            url: url,
            data: formData,
            headers: {
                'Accept': 'application/json',
                'Content-Type': undefined
            }
        }).then(function success(response) {
            getall();
            $('#modal-add-edit-shapefile').modal('hide');
            $('#idShapefile').val(-1);
        }, function error(response) {
            $('#modal-alert').modal({backdrop: 'static'});
            $('#modal-add-edit-shapefile').modal('hide');
        });
    };

    $scope.deleteModel = function () {
        if ($('#idToDelete').val() != -1) {
            $http({
                method: 'DELETE',
                url: 'shapefile/' + $('#idToDelete').val(),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                $('#modal-delete-shapefile').modal('hide');
                getall();
            }, function error(response) {
                console.log(response.statusText);
            });
        }
    };

    $scope.rechercher = function (page) {
        if ($scope.recherchePagination === 1) {
            $http({
                method: 'GET',
                url: 'rechercherGlobaleEspeceDcwPage?champ=' + $('#rechercheGlobale').val() + '&page=' + page,
                data: angular.toJson($scope.shapefile),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, page);
//            $('#pageEnCours').val(1);
            }, function error(response) {
                console.log(response);
            });
        } else {
            var formData = new FormData();
            var dwcs = $scope.shapefile.scientificname;
            if (dwcs == undefined)
                dwcs = null;

            var etat = $('[name="etat[]"]');
            var state = [];
            var etatS = "";
            var j = 0;
            for (var i = 0; i < etat.length; i++) {
                if (etat[i].checked == true) {
                    state[j] = etat[i].value;
                    j++;
                    etatS += etat[i].value;
                    if (i > 0) {
                        etatS += "&etat[]=" + etat[i].value;
                    }
                }
            }
            var validationMine = parseInt($('[name="validationMine"]').val());
            console.log(validationMine);
            if (isNaN(validationMine))
                validationMine = -999;

            console.log(dwcs);
            formData.append("dwcs", dwcs);
            formData.append("page", page);

            formData.append("validation", parseInt($('select[name=validation]').val()));
            formData.append("etat[]", state);
            formData.append("validationMine", validationMine);
            formData.append("espece", $('input[name=espece]').val());

            $http({
                method: 'POST',
                url: 'findByespeceDwcPaginatedSearch',
                data: formData,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': undefined
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, page);
//            $('#pageEnCours').val(page);
//            $scope.recherche = $scope.shapefile.scientificname;
            }, function error(response) {
                console.log(response.statusText);
            });
        }
    };

    $scope.rechercherFin = function () {
        console.log($scope.recherchePagination);
        if ($scope.recherchePagination === 1) {
            $http({
                method: 'GET',
                url: 'rechercherGlobaleEspeceDcwPage?champ=' + $('#rechercheGlobale').val() + '&page=' + $('#pageFin').val(),
                data: angular.toJson($scope.shapefile),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, $('#pageFin').val());
//            $('#pageEnCours').val(1);
            }, function error(response) {
                console.log(response);
            });
        } else {
            var formData = new FormData();
//        var dwcs = angular.toJson($scope.shapefile);                
//        var temp = dwcs.scientificName;
            var dwcs = $scope.shapefile.scientificName;
            if (dwcs == undefined)
                dwcs = null;
            console.log(dwcs);
            console.log($('#pageFin').val());

            var etat = $('[name="etat[]"]');
            var state = [];
            var etatS = "";
            var j = 0;
            for (var i = 0; i < etat.length; i++) {
                if (etat[i].checked == true) {
                    state[j] = etat[i].value;
                    j++;
                    etatS += etat[i].value;
                    if (i > 0) {
                        etatS += "&etat[]=" + etat[i].value;
                    }
                }
            }
            var validationMine = parseInt($('[name="validationMine"]').val());
            console.log(validationMine);
            if (isNaN(validationMine))
                validationMine = -999;


            formData.append("dwcs", dwcs);
            formData.append("page", $('#pageFin').val());

            formData.append("validation", parseInt($('select[name=validation]').val()));
            formData.append("etat[]", state);
            formData.append("validationMine", validationMine);
            formData.append("espece", $('input[name=espece]').val());

            $http({
                method: 'POST',
                url: 'findByespeceDwcPaginatedSearch',
                data: formData,
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': undefined
                }
            }).then(function success(response) {
                $scope.liste = response.data;
                paginer($scope.liste[0].total, 20, $('#pageFin').val());
//            $('#pageEnCours').val($('#pageFin').val());
                $scope.recherche = $scope.shapefile.scientificname;
            }, function error(response) {
                console.log(response.statusText);
            });
        }
    };

    $scope.rechercheGlobale = function () {
        $http({
            method: 'GET',
            url: 'rechercherGlobaleEspeceDcw?champ=' + $('#rechercheGlobale').val(),
            data: angular.toJson($scope.shapefile),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, 1);
            $scope.recherchePagination = 1;
//            $('#pageEnCours').val(1);
        }, function error(response) {
            console.log(response);
        });
    };

});

