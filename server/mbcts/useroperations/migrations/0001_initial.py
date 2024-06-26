# Generated by Django 5.0.3 on 2024-05-16 03:38

import django.db.models.deletion
from django.conf import settings
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='children',
            fields=[
                ('child_id', models.CharField(max_length=255, primary_key=True, serialize=False, unique=True)),
                ('firstname', models.CharField(max_length=100)),
                ('surname', models.CharField(max_length=100)),
                ('date_of_birth', models.DateField()),
                ('age', models.IntegerField()),
                ('number_of_guardians', models.IntegerField()),
            ],
        ),
        migrations.CreateModel(
            name='guardians',
            fields=[
                ('guardian_id', models.CharField(max_length=255, primary_key=True, serialize=False, unique=True)),
                ('firstname', models.CharField(max_length=255)),
                ('surname', models.CharField(max_length=255)),
                ('number_of_children', models.IntegerField()),
                ('address', models.CharField(max_length=255)),
                ('phonenumber', models.CharField(max_length=20)),
                ('phonenumber_2', models.CharField(max_length=20)),
                ('relationship', models.CharField(max_length=255)),
                ('user', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
        migrations.CreateModel(
            name='guardian_child_relationship',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('child_id', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='useroperations.children')),
                ('guardian_id', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='useroperations.guardians')),
            ],
        ),
    ]
