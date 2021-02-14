angular.module('app').controller('productsController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/store';
    const pageSize = 5;
    const maxPages = 8;
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.firstPage = 1;

    $scope.fillProducts = function () {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                page: $scope.currentPage-1,
                size: pageSize,
                min: $scope.productFilter ? $scope.productFilter.minPrice : null,
                max: $scope.productFilter ? $scope.productFilter.maxPrice : null,
                title: $scope.productFilter ? $scope.productFilter.title : null
            }
        }).then(function (response) {
            $scope.productList = response.data.content;
            $scope.currentPage = response.data.number + 1;
            if ($scope.firstPage > $scope.currentPage) {
                $scope.firstPage = $scope.currentPage;
            }
            $scope.totalPages = response.data.totalPages;
            $scope.fillPages()
        })
    }

    $scope.filterProducts = function () {
        $scope.currentPage = 1;
        $scope.fillProducts();
    }

    $scope.clearFilter = function () {
        $scope.productFilter = null;
        $scope.filterProducts();
    }

    $scope.fillPages = function () {
        let printPages = [];
        let mPages = Math.min(maxPages, $scope.totalPages);
        for (let i = 0; i < mPages; i++) {
            printPages.push(i + $scope.firstPage);
        }

        $scope.printPages = printPages;
    }

    $scope.toPage = function (page) {
        $scope.currentPage = page;
        $scope.fillProducts();
    }

    $scope.prevPages = function () {
        if ($scope.firstPage === 1) return;
        $scope.firstPage --;
        if ($scope.currentPage > $scope.getLastPage()) {
            $scope.currentPage = $scope.getLastPage();
        }
        $scope.fillProducts();
    }

    $scope.nextPages = function () {
        if ($scope.getLastPage() >= $scope.totalPages) return;
        $scope.firstPage ++;
        if ($scope.currentPage < $scope.firstPage) {
            $scope.currentPage = $scope.firstPage;
        }
        $scope.fillProducts();
    }

    $scope.getLastPage = function() {
        return $scope.firstPage + Math.min(maxPages, $scope.totalPages);
    }

    $scope.addToCart = function (id, quantity) {
        $http({
            url: contextPath + '/api/v1/cart/add',
            method: 'GET',
            params: {
                id: id,
                quantity: quantity
            }
        }).then(function () {

        });
    }

    $scope.fillProducts();
});