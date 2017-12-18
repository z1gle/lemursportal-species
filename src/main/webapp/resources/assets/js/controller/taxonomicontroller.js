/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller("taxonomi",function($scope,$http){
    $scope.liste=[];
    $scope.recherche="";
    $scope.taxonomi={};
    $scope.total;
    $scope.form={};
    getall();
    
    function getall(){
        $http({
            method : 'POST',
            url : 'findByespeceTaxo',
            data : angular.toJson($scope.taxonomi),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function success(response){
            $scope.liste=response.data;
        },function error(response){
            console.log(response.statusText);
        });
    }
    
    $scope.rechercher=function(){
        $http({
            method : 'POST',
            url : 'findByespeceTaxo',
            data : angular.toJson($scope.taxonomi),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function success(response){
            $scope.liste=response.data;
            $scope.recherche=$scope.taxonomi.scientificname;
        },function error(response){
            console.log(response.statusText);
        });
    };
    
    $scope.editer=function(darwin){
        $("#editOrnew").modal({backdrop : 'static'});
        $scope.form=angular.copy(darwin);
    };
    
    $scope.annuler=function(){
        $("#editOrnew").modal("hide");
        $scope.form={};
    };
    
    $scope.save=function(){
        $http({
            method : 'POST',
            url : 'saveTaxo',
            data : angular.toJson($scope.form),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function success(response){
            $scope.form={};
            $("#editOrnew").modal("hide");
            getall();
        },function error(response){
            console.log(response.statusText);
        });
    };
    
    $scope.delete=function(taxonomi){
        $http({
            method : 'POST',
            url : 'deleteTaxo',
            data : angular.toJson(taxonomi),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then(function success(response){
            getall();
        },function error(response){
            console.log(response.statusText);
        });
    };
});