/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("darwin", function ($scope, $http) {
    $scope.liste = [];
    $scope.darwin = {};
    $scope.recherche = "";
    $scope.pages = [];
    $scope.recherchePagination = 0;
    $scope.pageEnCours = 0;
    $scope.lastPage = 0;
    findForValidation();

    function paginer(liste, pas, position) {
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

    function findForValidation() {
        $http({
            method: 'POST',
            url: 'findForValidation',
            data: {
                validationexpert: -1
            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            $scope.recherche = $scope.darwin.scientificname;
            paginer($scope.liste[0].total, 20, 1);
            $scope.recherchePagination = 0;
            $("#loader-spinner").hide();
        }, function error(response) {
            console.log(response.statusText);
            $("#loader-spinner").hide();
        });
    }
    
    $scope.ffv = function () {
        $http({
            method: 'POST',
            url: 'findForValidation',
            data: {
                validationexpert: -1
            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            $scope.recherche = $scope.darwin.scientificname;
            paginer($scope.liste[0].total, 20, 1);
            $scope.recherchePagination = 0;
            $("#loader-spinner").hide();
        }, function error(response) {
            console.log(response.statusText);
            $("#loader-spinner").hide();
        });
    }

    $scope.rechercher = function (page) {
        $http({
            method: 'POST',
            url: 'findForValidationGlobal?page=' + page,
            data: {
                validationexpert: -1,
                champ: $('#rechercheGlobale').val()
            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, page);
        }, function error(response) {
            console.log(response);
        });
    };

    $scope.rechercherFin = function () {
        $http({
            method: 'POST',
            url: 'findForValidationGlobal?page=' + $('#pageFin').val(),
            data: {
                validationexpert: -1,
                champ: $('#rechercheGlobale').val()
            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, $('#pageFin').val());
        }, function error(response) {
            console.log(response);
        });
    };

    $scope.rechercheGlobale = function () {
        $http({
            method: 'POST',
            url: 'findForValidationGlobal',
            data: {
                validationexpert: -1,
                champ: $('#rechercheGlobale').val()
            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $scope.liste = response.data;
            paginer($scope.liste[0].total, 20, 1);
            $scope.recherchePagination = 1;
        }, function error(response) {
            console.log(response);
        });
    };

});

