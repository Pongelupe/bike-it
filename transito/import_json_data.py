#!/usr/bin/env python:
import json 
import psycopg2

with open('transito/data/temporealcoordenadaonibus_2020.json') as json_file:
    con = psycopg2.connect(host='localhost', port=25432, database='mob',
            user='mob', password='mob')
    cursor = con.cursor()

    sql = """
    INSERT INTO public.onibus
    (geom, num_veic, linha)
    VALUES(ST_MakePoint(%s, %s), %s, %s)
"""

    data = json.load(json_file)
    for p in data:
        print(p)
        cursor.execute(sql, (p['LT'], p['LG'], p['NV'], p.get('NL',-1), ))

    con.commit()
    cursor.close()
