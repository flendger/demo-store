angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/store';
    const pageSize = 5;
    const maxPages = 8;
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.firstPage = 1;
    $scope.authorized = false;
    $scope.isShowAuth = false;

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.authorized = true;
                    $scope.fillCart();
                }
            }, function errorCallback() {
                window.alert("Error");
            });
    };

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

    $scope.delete = function (id) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'DELETE',
            params: {
                id: id
            }
        }).then($scope.fillProducts);
    }

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function (){
                $scope.newProduct = null;
                $scope.fillProducts();
            });
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
            $scope.fillCart();
        });
    }

    $scope.decFromCart = function (id, quantity) {
        $http({
            url: contextPath + '/api/v1/cart/remove',
            method: 'GET',
            params: {
                id: id,
                quantity: quantity
            }
        }).then(function () {
            $scope.fillCart();
        });
    }

    $scope.deleteFromCart = function (id) {
        $http({
            url: contextPath + '/api/v1/cart/delete',
            method: 'GET',
            params: {
                id: id
            }
        }).then(function () {
            $scope.fillCart();
        });
    }

    $scope.fillCart = function () {
        $http.get(contextPath + "/api/v1/cart")
            .then(function (response) {
                $scope.cartList = response.data.items;
                $scope.cartQuantity = response.data.quantity;
                $scope.cartSum = response.data.sum;
            })
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

    $scope.getOrder = function () {

    }

    $scope.fillProducts();
});