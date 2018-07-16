/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("darwin", function ($scope, $http) {
    $scope.liste = [];
    $scope.pagination = new Object();
    $scope.page = 1;
    $scope.dwc = new Object();
    $scope.total = 0;
    $scope.page = 0;
    $scope.lastPage = 0;
    modelePourFaireMarcherOnChange = {};
    getall();

    function getall() {
        var dwc = new Object();
//        dwc.id = 6;
//        dwc.dwcyear = 1999;
        var page = 1;
        $http({
            method: 'POST',
            url: '/species/observations?page=' + page,
            data: dwc,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $("#loader-spinner").hide();
            $scope.liste = response.data.retour.liste;
            $scope.total = response.data.retour.total;
            $scope.page = response.data.retour.page;
            $scope.lastPage = response.data.retour.lastPage;
            paginer(response.data.retour);
        }, function error(response) {
            console.log(response);
            $("#loader-spinner").hide();
        });
    }
    function paginer(liste) {
        console.log(liste);
        var previous = 1;
        var next = liste.lastPage;
        if (liste.page > 2)
            previous = liste.page - 1;
        if (liste.page < liste.lastPage)
            next = liste.page + 1;
        $scope.pagination.table = [];
        $scope.pagination.debut = 1;
        $scope.pagination.previous = previous;
        if (liste.page < 6) {
            for (var i = 1; i < 6 && i < liste.lastPage; i++) {
                $scope.pagination.table.push(i);
            }
        } else if (liste.page > liste.lastPage - 5) {
            for (var i = liste.lastPage - 4; i <= liste.lastPage; i++) {
                $scope.pagination.table.push(i);
            }
        } else {
            for (var i = liste.page - 2; i <= liste.page + 2; i++) {
                $scope.pagination.table.push(i);
            }
        }
        $scope.pagination.next = next;
        $scope.pagination.fin = liste.lastPage;
    }
    $scope.findAll = function (pages) {
        var dwc = new Object();
        $http({
            method: 'POST',
            url: '/species/observations?page=' + pages,
            data: dwc,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $("#loader-spinner").hide();
            $scope.liste = response.data.retour.liste;
            $scope.total = response.data.retour.total;
            $scope.page = response.data.retour.page;
            $scope.lastPage = response.data.retour.lastPage;
            paginer(response.data.retour);
        }, function error(response) {
            console.log(response);
            $("#loader-spinner").hide();
        });
    };
    $scope.search = function (pages) {
        var dwc = new Object();

        var scientificname = $('#species').val();
        if (scientificname !== undefined && scientificname !== null && scientificname !== '' && scientificname !== ' ') {
            dwc.scientificname = scientificname;
        }

        var publique = $('#publique:checked').val();
        var privee = $('#privee:checked').val();                 
        if (publique) {
            if (!privee) {
                console.log(publique);
                dwc.publique = 'true';
                console.log(dwc);
            }                
        } else {
            if (privee) {
                console.log(publique);
                dwc.publique = 'false';
                console.log(dwc);
            }                
        }

        var etat = $('#etat').val();
        if (etat !== '-999')
            dwc.validationexpert = parseInt(etat);
        console.log(dwc);
        $http({
            method: 'POST',
            url: '/species/observations?page=' + pages,
            data: dwc,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            $("#loader-spinner").hide();
            $scope.liste = response.data.retour.liste;
            $scope.total = response.data.retour.total;
            $scope.page = response.data.retour.page;
            $scope.lastPage = response.data.retour.lastPage;
            paginer(response.data.retour);
        }, function error(response) {
            console.log(response);
            $("#loader-spinner").hide();
        });
    };
});

