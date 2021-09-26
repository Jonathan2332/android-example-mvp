package com.example.mvp.presenter

interface BaseView<T, V>
{
    var presenter: T
    var binding: V
}