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
import random

# Create your views here.
@api_view(['POST'])
@csrf_exempt
def home_tip(request):
    data = dict(request.data)
    count = safety_tip.objects.all().count()
    tip_id = random.choice(range(1, count))
    tip = safety_tip.objects.filter(id = tip_id)
    serializer = Safety_tipSerializer(tip, many = True)
    return Response({'tip' : serializer.data[0]['tip']})
    