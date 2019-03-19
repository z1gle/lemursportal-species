/* ﻿
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("draft", function ($scope, $http) {
    $scope.liste1 = [];
    $scope.liste2 = [];
    $scope.liste3 = [];
    $scope.total;
    $scope.t1;
    $scope.t2;
    $scope.t3;
    getall(-1);
    function getall(sc) {
        $http({
            method: 'GET',
            url: 'drafts?scientificname=' + sc,
            data: angular.toJson($scope.taxonomi),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            console.log(response);
            $scope.liste1 = response.data[0];
            $scope.liste2 = response.data[1];
            $scope.liste3 = response.data[2];
            $(".lsp").hide();
            $scope.t1 = calcSum(response.data[0]);
            $scope.t2 = calcSum(response.data[1]);
            $scope.t3 = calcSum(response.data[2]);
            $scope.total = $scope.t1 + $scope.t2 + $scope.t3;
        }, function error(response) {
            console.log(response.statusText);
            $(".lsp").hide();
        });
    }
    
    function calcSum(list) {
        var valiny = 0;
        for (var i = 0; i < list.length; i++) {
            valiny += parseInt(list[i].tout);
        }
        return valiny;
    }

    $scope.get = function (sc) {
        $http({
            method: 'GET',
            url: 'drafts?scientificname=' + sc,
            data: angular.toJson($scope.taxonomi),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function success(response) {
            console.log(response);
            $scope.liste1 = response.data[0];
            $scope.liste2 = response.data[1];
            $scope.liste3 = response.data[2];
            $(".lsp").hide();
            $scope.t1 = calcSum(response.data[0]);
            $scope.t2 = calcSum(response.data[1]);
            $scope.t3 = calcSum(response.data[2]);
            $scope.total = $scope.t1 + $scope.t2 + $scope.t3;
        }, function error(response) {
            console.log(response.statusText);
            $(".lsp").hide();
        });
    };
});