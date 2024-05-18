from django.db import models
from useroperations.models import *

# Create your models here.
class incident(models.Model):
    child_id = models.ForeignKey(children,on_delete=models.CASCADE)
    time_stamp = models.DateTimeField( auto_now=True, auto_now_add=False)
    Location = models.CharField(max_length=255)
    
class routine(models.Model):
    child_id = models.ForeignKey(children,on_delete=models.CASCADE,  null=True)
    day = models.CharField(max_length=100)
    variance  = models.CharField(max_length=100)
    coordinate_00 = models.CharField(max_length=255)
    coordinate_01 = models.CharField(max_length=255)
    coordinate_02 = models.CharField(max_length=255)
    coordinate_03 = models.CharField(max_length=255)
    coordinate_04 = models.CharField(max_length=255)
    coordinate_05 = models.CharField(max_length=255)
    coordinate_06 = models.CharField(max_length=255)
    coordinate_07 = models.CharField(max_length=255)
    coordinate_08 = models.CharField(max_length=255)
    coordinate_09 = models.CharField(max_length=255)
    coordinate_10 = models.CharField(max_length=255)
    coordinate_11 = models.CharField(max_length=255)
    coordinate_12 = models.CharField(max_length=255)
    coordinate_13 = models.CharField(max_length=255)
    coordinate_14 = models.CharField(max_length=255)
    coordinate_15 = models.CharField(max_length=255)
    coordinate_16 = models.CharField(max_length=255)
    coordinate_17 = models.CharField(max_length=255)
    coordinate_18 = models.CharField(max_length=255)
    coordinate_19 = models.CharField(max_length=255)
    coordinate_20 = models.CharField(max_length=255)
    coordinate_21 = models.CharField(max_length=255)
    coordinate_22 = models.CharField(max_length=255)
    coordinate_23 = models.CharField(max_length=255)
    
class account_status(models.Model):
    child_id = models.ForeignKey(children,on_delete=models.CASCADE,  null=True)
    guardian_id = models.ForeignKey(guardians, on_delete=models.CASCADE, null = True)
    live_location = models.CharField(max_length=255)
    Geo_fenced = models.CharField(max_length=255, null=True)
    routine_training = models.CharField(max_length=255)
    panic = models.BooleanField()
    routine = models.ForeignKey(routine, on_delete=models.SET_NULL, null = True)
    battery = models.CharField(max_length=255)
    last_update = models.DateTimeField()