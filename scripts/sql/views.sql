CREATE MATERIALIZED VIEW distance_per_segment_central
AS
SELECT id,
       COUNT(id),
       MIN(c),
       MAX(c)
FROM (SELECT id,
             (SELECT MIN(mind.d)
              FROM (SELECT ST_DistanceSphere(st_force3d ((dp).geom),c.geom) AS d
                    FROM coordinates c
                    WHERE c.cicloway = TRUE) AS mind) AS c
      FROM (SELECT S.id,
                   ST_DumpPoints(ST_AsEWKT (ST_LineFromEncodedPolyline (POLYLINE))) AS dp
            FROM segment S
            WHERE s.id IN (SELECT DISTINCT SIXS.id_segment
                           FROM search_item SI
                             JOIN search_itemxsegment SIXS ON SI.id = SIXS.id_search_item
                           WHERE SI.id_search IN (15))) AS foo) AS bar
GROUP BY id
ORDER BY id WITH NO DATA;



REFRESH MATERIALIZED VIEW distance_per_segment_central;
DROP MATERIALIZED VIEW distance_per_segment_central;

CREATE MATERIALIZED VIEW distance_per_segment_pampulha
AS
SELECT id,
       COUNT(id),
       MIN(c),
       MAX(c)
FROM (SELECT id,
             (SELECT MIN(mind.d)
              FROM (SELECT ST_DistanceSphere(st_force3d ((dp).geom),c.geom) AS d
                    FROM coordinates c
                    WHERE c.cicloway = TRUE) AS mind) AS c
      FROM (SELECT S.id,
                   ST_DumpPoints(ST_AsEWKT (ST_LineFromEncodedPolyline (POLYLINE))) AS dp
            FROM segment S
            WHERE s.id IN (SELECT DISTINCT SIXS.id_segment
                           FROM search_item SI
                             JOIN search_itemxsegment SIXS ON SI.id = SIXS.id_search_item
                           WHERE SI.id_search IN (16))) AS foo) AS bar
GROUP BY id
ORDER BY id WITH NO DATA;



REFRESH MATERIALIZED VIEW distance_per_segment_pampulha;
DROP MATERIALIZED VIEW distance_per_segment_pampulha;

CREATE MATERIALIZED VIEW distance_per_segment_orla_pampulha
AS
SELECT id,
       COUNT(id),
       MIN(c),
       MAX(c)
FROM (SELECT id,
             (SELECT MIN(mind.d)
              FROM (SELECT ST_DistanceSphere(st_force3d ((dp).geom),c.geom) AS d
                    FROM coordinates c
                    WHERE c.cicloway = TRUE) AS mind) AS c
      FROM (SELECT S.id,
                   ST_DumpPoints(ST_AsEWKT (ST_LineFromEncodedPolyline (POLYLINE))) AS dp
            FROM segment S
            WHERE s.id IN (SELECT DISTINCT SIXS.id_segment
                           FROM search_item SI
                             JOIN search_itemxsegment SIXS ON SI.id = SIXS.id_search_item
                           WHERE SI.id_search IN (17))) AS foo) AS bar
GROUP BY id
ORDER BY id WITH NO DATA;

REFRESH MATERIALIZED VIEW distance_per_segment_orla_pampulha;
DROP MATERIALIZED VIEW distance_per_segment_orla_pampulha;
