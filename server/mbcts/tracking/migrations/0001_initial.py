# Generated by Django 5.0.3 on 2024-05-17 08:21

import django.db.models.deletion
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('useroperations', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='incident',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('time_stamp', models.DateTimeField(auto_now=True)),
                ('Location', models.CharField(max_length=255)),
                ('child_id', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='useroperations.children')),
            ],
        ),
        migrations.CreateModel(
            name='routine',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('day', models.CharField(max_length=100)),
                ('variance', models.CharField(max_length=100)),
                ('coordinate_00', models.CharField(max_length=255)),
                ('coordinate_01', models.CharField(max_length=255)),
                ('coordinate_02', models.CharField(max_length=255)),
                ('coordinate_03', models.CharField(max_length=255)),
                ('coordinate_04', models.CharField(max_length=255)),
                ('coordinate_05', models.CharField(max_length=255)),
                ('coordinate_06', models.CharField(max_length=255)),
                ('coordinate_07', models.CharField(max_length=255)),
                ('coordinate_08', models.CharField(max_length=255)),
                ('coordinate_09', models.CharField(max_length=255)),
                ('coordinate_10', models.CharField(max_length=255)),
                ('coordinate_11', models.CharField(max_length=255)),
                ('coordinate_12', models.CharField(max_length=255)),
                ('coordinate_13', models.CharField(max_length=255)),
                ('coordinate_14', models.CharField(max_length=255)),
                ('coordinate_15', models.CharField(max_length=255)),
                ('coordinate_16', models.CharField(max_length=255)),
                ('coordinate_17', models.CharField(max_length=255)),
                ('coordinate_18', models.CharField(max_length=255)),
                ('coordinate_19', models.CharField(max_length=255)),
                ('coordinate_20', models.CharField(max_length=255)),
                ('coordinate_21', models.CharField(max_length=255)),
                ('coordinate_22', models.CharField(max_length=255)),
                ('coordinate_23', models.CharField(max_length=255)),
                ('child_id', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='useroperations.children')),
            ],
        ),
        migrations.CreateModel(
            name='account_status',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('live_location', models.CharField(max_length=255)),
                ('Geo_fenced', models.CharField(max_length=255, null=True)),
                ('routine_training', models.CharField(max_length=255)),
                ('panic', models.BooleanField()),
                ('battery', models.CharField(max_length=255)),
                ('last_update', models.DateTimeField()),
                ('child_id', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='useroperations.children')),
                ('guardian_id', models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='useroperations.guardians')),
                ('routine', models.ForeignKey(null=True, on_delete=django.db.models.deletion.SET_NULL, to='tracking.routine')),
            ],
        ),
    ]
