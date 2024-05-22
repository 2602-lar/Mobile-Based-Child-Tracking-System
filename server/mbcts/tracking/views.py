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
import json

# Create your views here.
# Create your views here.
#view for panic
@api_view(['POST'])
@csrf_exempt
def child_panic(request):
    data = dict(request.data)
    child_id  = data['child_id'][0]
    status = account_status.objects.get(child_id = child_id)
    try:
        status.panic = True
        status.save()
    except:
        Response({"received" : "failure"})
    return Response({"received" : "request"})
    
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
        Response({"received" : "failure"})
    return Response({"received" : "request"})

@api_view(['POST'])
@csrf_exempt
def live_location_parent(request):
    data = dict(request.data)
    guardian_id  = data['guardian_id'][0]
    status = account_status.objects.filter(guardian_id = guardian_id)
    serializer = PruneAccount_StatusSerializer(status, many = True)
    location = serializer.data[0]['live_location']
    json_string = location.replace("'", "\"")
    location = json.loads(json_string)
    return Response({'longitude' : str(location['longitude']), 'latitude' : str(location['latitude'])})

@api_view(['POST'])
@csrf_exempt
def submit_distance(request):
    data = dict(request.data)
    guardian_id  = data['guardian_id'][0]
    distance = data['distance'][0]
    status = account_status.objects.get(guardian_id = guardian_id)
    try:
        status.Geo_fence_distance = distance
        status.Geo_fenced_status =  "within_fence"
        status.Geo_fenced = "True"
        status.save()
    except:
        res = "not saved"
    return Response({"received" : "request"})

@api_view(['POST'])
@csrf_exempt
def account_stats_parent(request):
    data = dict(request.data)
    guardian_id  = data['guardian_id'][0]
    status = account_status.objects.filter(guardian_id = guardian_id)
    serializer = PruneAccount_StatusSerializer(status, many = True)
    return JsonResponse(serializer.data, safe= False)

@api_view(['POST'])
@csrf_exempt
def account_stats_child(request):
    data = dict(request.data)
    child_id  = data['child_id'][0]
    status = account_status.objects.filter(child_id = child_id)
    serializer = PruneAccount_StatusSerializer(status, many = True)
    return JsonResponse(serializer.data, safe= False)

@api_view(['POST'])
@csrf_exempt
def panic_attack(request):
    data = dict(request.data)
    child_id  = data['child_id'][0]
    status = account_status.objects.get(child_id = child_id)
    status.panic = True
    status.save()
    return Response({"received" : "request"})