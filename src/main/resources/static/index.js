angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/store/api/v1';
    const pageSize = 5;
    $scope.currentPage = 0;
    $scope.totalPages = 1;

    $scope.prevPage = function () {
        if ($scope.currentPage === 0) return;
        $scope.currentPage --;
        $scope.fillProducts();
    }

    $scope.nextPage = function () {
        if ($scope.currentPage + 1 === $scope.totalPages) return;
        $scope.currentPage ++;
        $scope.fillProducts();
    }

    $scope.fillProducts = function () {
        console.log($scope.productFilter);
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                page: $scope.currentPage,
                size: pageSize,
                min: $scope.productFilter ? $scope.productFilter.minPrice : null,
                max: $scope.productFilter ? $scope.productFilter.maxPrice : null,
                title: $scope.productFilter ? $scope.productFilter.title : null
            }
        }).then(function (response) {
            $scope.productList = response.data.content;
            $scope.currentPage = response.data.number;
            $scope.totalPages = response.data.totalPages;
        })
    }

    $scope.delete = function (id) {
        $http({
            url: contextPath + '/products',
            method: 'DELETE',
            params: {
                id: id
            }
        }).then($scope.fillProducts);
    }

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
        .then(function (){
            $scope.newProduct = null;
            $scope.fillProducts();
        });
    }

    $scope.fillProducts();
});