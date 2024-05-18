from .models import *
from .serializers import *
from rest_framework.response import Response
from rest_framework import viewsets
from django.contrib.auth.models import User
from .serializers import *
from django.shortcuts import render, HttpResponse, redirect, get_object_or_404
from rest_framework.decorators import api_view
from django.views.decorators.csrf import csrf_exempt
from .auxillary import *
from django.contrib.auth.hashers import make_password
from django.http import HttpResponseBadRequest
from tracking import serializers

# CRUD for guardians
class GuardianViewSet(viewsets.ModelViewSet):

    queryset = guardians.objects.all()
    serializer_class = GuardiansSerializer
    
    def retreive(self, request, pk=None):
         item = get_object_or_404(self.queryset, audited_id=pk)
         print(item)
         serializer = GuardiansSerializer(item)
         return Response(serializer.data)

    def perform_create(self, serializer):
        print(self.data)
        id = generate_id(guardians, 'GUA')
        serializer.save(guardian_id = id)

    def perform_update(self, serializer):
        serializer.save()

    def destroy(self, request, id=None):
        guardian = guardians.objects.get(id=id)
        guardian.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
   
# CRUD for children
class ChildrenViewSet(viewsets.ModelViewSet):

    queryset = children.objects.all()
    serializer_class = ChildrenSerializer
    
    def retreive(self, request, pk=None):
         item = get_object_or_404(self.queryset, audited_id=pk)
         print(item)
         serializer = ChildrenSerializer(item)
         return Response(serializer.data)

    def perform_create(self, serializer):
        id = generate_id(children, 'CHI')
        serializer.save(child_id = id)

    def perform_update(self, serializer):
        serializer.save()

    def destroy(self, request, id=None):
        child = children.objects.get(id=id)
        child.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)



# CRUD view for users
class UserViewSet(viewsets.ModelViewSet):

    queryset = User.objects.all()
    serializer_class = UserSerializer

    def perform_create(self, serializer):
        serializer.save()

    def perform_update(self, serializer):
        serializer.save()

    def destroy(self, request, id=None):
        user = User.objects.get(id=id)
        user.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
    
    @api_view(['GET', 'POST'])
    def users(request):
        queryset = User.objects.all()
        serializers = UserSerializer(queryset, many=True)
        return Response(serializers.data)


# CRUD for children
class ChildrenGuardiansViewSet(viewsets.ModelViewSet):

    queryset = guardian_child_relationship.objects.all()
    serializer_class = ChildrenGuardianSerializer
    
    def retreive(self, request, pk=None):
         item = get_object_or_404(self.queryset, audited_id=pk)
         print(item)
         serializer = ChildrenGuardianSerializer(item)
         return Response(serializer.data)

    def perform_create(self, serializer):
        serializer.save()

    def perform_update(self, serializer):
        serializer.save()

    def destroy(self, request, id=None):
        child = guardian_child_relationship.objects.get(id=id)
        child.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

@api_view(['POST'])
@csrf_exempt
def creating_parent(request):
    data = dict(request.data)
    guardian_id = generate_id(guardians, 'G')
    create_guardian = PruneGuardiansSerializer( data = {
            'guardian_id' : guardian_id,
            'firstname' : data['name'][0],
            'surname' : data['surname'][0],
            'number_of_children' : data['number_of_children'][0],
            'address' : data['address'][0],
            'phonenumber' : data['phonenumber'][0],
            'phonenumber_2' : data['phonenumber_2'][0],
            'relationship' : data['relationship'][0]
    })
    if create_guardian.is_valid() :
        create_guardian.save()
        print('created ', guardian_id)
        return Response({'guardian_id' : guardian_id})
    else:
        return HttpResponseBadRequest({'message' : 'error'})

@api_view(['POST'])
@csrf_exempt
def creating_child(request):
    data = dict(request.data)
    print(data)
    child_id = generate_id(children, 'CH')
    create_child = ChildrenSerializer(data = {
        'child_id' : child_id,
        'firstname' : data['firstname'][0],
        'surname' : data['surname'][0],
        'date_of_birth' : data['date_of_birth'][0],
        'age' : data['age'][0],
        'number_of_guardians' : data['number_of_guardians'][0]
    })
    if create_child.is_valid():
        print('saving child : ', child_id)
        create_child.save()
        relationship = serializers.PruneAccount_StatusSerializer(data = {
            'child_id' : child_id,
            'guardian_id' : data['guardian_id'][0],
            'live_location' : 'chdib',
            'Geo_fenced' : 'ydibibibufwi',
            'routine_training' : 'gvfyrufeie',
            'panic' :  False,
            'routine' : None,
            'battery' : "40%",
            'last_update' : datetime.now()
        })
        if relationship.is_valid():
            print('saving relationship : ', child_id , ' guardian_id : ', data['guardian_id'][0])
            relationship.save()
            return Response({'guardian_id' : data['guardian_id'][0], 'child_id' : child_id})
        else:
            return HttpResponseBadRequest({'message' : 'error saving relationship'})
    else:
        return HttpResponseBadRequest({'message' : 'error saving child'})

@api_view(['POST'])
@csrf_exempt
def creating_application_user(request):
    data = dict(request.data)
    print(data)
    create_user = Create_UserSerializer(data = {
                'username' : data['username'][0],
                'password' : make_password(data['password'][0]),
                "is_superuser": False,
                "first_name": "",
                "last_name": "",
                "email": "",
                "is_staff": True,
                "is_active": True,
                "date_joined": datetime.now(),
                "groups": [],
                "user_permissions": []
    })
    if create_user.is_valid():
         print('creating user')
         create_user.save()
         guardian_id = data['username'][0]
         guardian = guardians.objects.get(guardian_id = guardian_id)
         user = User.objects.get(username = guardian_id)
         guardian.user = user
         guardian.save()
         return Response({'message' : 'User Successfully created'})
    else:
        return HttpResponseBadRequest({'message' : 'error saving user!'})