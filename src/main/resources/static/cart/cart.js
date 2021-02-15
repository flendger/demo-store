angular.module('app').controller('cartController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/store';
    let msgTxt = "";

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

    $scope.clearCart = function () {
        $http.get(contextPath + "/api/v1/cart/clear")
            .then(function () {
                $scope.fillCart();
            })
    }

    $scope.fillCart = function () {
        $http.get(contextPath + "/api/v1/cart")
            .then(function (response) {
                $scope.cartList = response.data.items;
                $scope.cartQuantity = response.data.quantity;
                $scope.cartSum = response.data.sum;
                $scope.cartEmpty();
            })
    }

    $scope.placeOrder = function () {
        $http.post(contextPath + "/api/v1/orders", $scope.address)
            .then(function successCallback(response) {
                $scope.address = null;
                msgTxt = "Заказ №" + response.data.id + " от " + $scope.getLocaleDate(response.data.date) + " на сумму " + response.data.sum + " оформлен";
                $('#infoModal').modal('show');
                $location.path('/');
                $scope.fillCart();
            }, function errorCallback(response) {
                msgTxt = response.data.message;
                $('#infoModal').modal('show');
            })
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
            $scope.cartEmpty();
            $scope.fillCart();
        });
    }

    $('#infoModal').on('show.bs.modal', function () {
        const modal = $(this);
        modal.find('.info-text').text(msgTxt)
    })

    $scope.fillCart();
});