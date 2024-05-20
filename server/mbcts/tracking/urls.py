from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import views

urlpatterns = [
    path('live-location/', views.live_location, name = 'users'),
]