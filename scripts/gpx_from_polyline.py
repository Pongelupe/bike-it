#!/usr/bin/env python3
import datetime
import gpxpy
import gpxpy.gpx 
from polyline.codec import PolylineCodec
import os
import psycopg2
import sys


def polyline_to_gpx(polyline = None):
    if polyline == None:
        raise Exception("Need a Google Polyline as parameter")

    waypoints = None
    try:
        waypoints = PolylineCodec().decode(polyline)
    except Exception as e:
        raise Exception(f"Error decoding polyline. err: {e}")

    gpx = gpxpy.gpx.GPX()
    gpx.creator = "Ride with gpxpy"

    for point in waypoints:
        lat, lon = point
        gpx.waypoints.append(gpxpy.gpx.GPXWaypoint(lat, lon))

    return gpx.to_xml()


def load_polylines(id_search):
    con = psycopg2.connect(host='localhost', database='bikeit',
            user='postgres', password='root')
    cursor = con.cursor()
    cursor.execute("""SELECT DISTINCT POLYLINE FROM SEGMENT S 
                JOIN SEARCH_ITEMXSEGMENT SIXS ON SIXS.ID_SEGMENT = S.ID
                JOIN SEARCH_ITEM SI ON SI.ID = SIXS.ID_SEARCH_ITEM
                WHERE SI.ID_SEARCH = %s
                """, (id_search,))
    recset = cursor.fetchall()
    os.mkdir(f'gpx/{id_search}')
    i = 0
    for rec in recset:
        i+=1
        now = datetime.datetime.now()
        f = open(f'gpx/{id_search}/{i}_{now}.gpx', 'w')
        f.write(polyline_to_gpx(rec[0]))
        print(f'{i} - persisted @ gpx/{id_search}/{i}_{now}.gpx')
        f.close()


if __name__ == '__main__':
    os.makedirs("gpx", exist_ok=True)
    load_polylines(sys.argv[1]);

