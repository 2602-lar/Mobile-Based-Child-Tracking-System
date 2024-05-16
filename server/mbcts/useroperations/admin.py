from django.contrib import admin
from .models import *

# Register your models here.
admin.site.register(guardians)
admin.site.register(children)
admin.site.register(guardian_child_relationship)