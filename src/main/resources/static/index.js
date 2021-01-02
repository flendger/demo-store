angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/store';

    $scope.fillProducts = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET'
        }).then(function (response) {
            $scope.productList = response.data;
        })
    }

    $scope.delete = function (id) {
        $http({
            url: contextPath + '/products/delete',
            method: 'POST',
            params: {
                id: id
            }
        }).then($scope.fillProducts);
    }

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products/add', $scope.newProduct)
        .then(function (response){
            $scope.newProduct = null;
            $scope.fillProducts();
        });
    }

    $scope.fillProducts();
});