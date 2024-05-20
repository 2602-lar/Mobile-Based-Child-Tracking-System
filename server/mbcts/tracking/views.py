from .models import *
from .serializers import *
from rest_framework.response import Response
from rest_framework import viewsets
from django.contrib.auth.models import User
from .serializers import *
from django.shortcuts import render, HttpResponse, redirect, get_object_or_404
from rest_framework.decorators import api_view
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.hashers import make_password
from django.http import HttpResponseBadRequest,  JsonResponse

# Create your views here.
# Create your views here.
@api_view(['POST'])
@csrf_exempt
def child_panic(request):
    data = dict(request.data)
    child_id  = data['child_id'][0]
    
@api_view(['POST'])
@csrf_exempt
def live_location(request):
    data = dict(request.data)
    child_id  = data['child_id'][0]
    latitude = data['latitude'][0]
    longitude  = data['longitude'][0]
    location = str({'latitude':latitude, 'longitude' : longitude})
    status = account_status.objects.get(child_id = child_id)
    try:
        status.live_location = location
        status.save()
    except:
        print("error saving data")
    #print("Child ID : ", child_id, "latitude : ", latitude, "longitude : ", longitude)
    return Response({"received" : "request"})