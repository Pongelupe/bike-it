--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.11
-- Dumped by pg_dump version 11.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE bikeit;
--
-- Name: bikeit; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE bikeit WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C.UTF-8' LC_CTYPE = 'C.UTF-8';


ALTER DATABASE bikeit OWNER TO postgres;

\connect bikeit

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: topology; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA topology;


ALTER SCHEMA topology OWNER TO postgres;

--
-- Name: SCHEMA topology; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA topology IS 'PostGIS Topology schema';


--
-- Name: hstore; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS hstore WITH SCHEMA public;


--
-- Name: EXTENSION hstore; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION hstore IS 'data type for storing sets of (key, value) pairs';


--
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry, geography, and raster spatial types and functions';


--
-- Name: postgis_topology; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis_topology WITH SCHEMA topology;


--
-- Name: EXTENSION postgis_topology; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis_topology IS 'PostGIS topology spatial types and functions';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: coordinates; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.coordinates (
    id integer NOT NULL,
    geom public.geometry(PointZ,4326),
    "NOME" character varying(60),
    "NUM_BAIRRO" bigint,
    "ID_BAIRRO" double precision,
    vertex_index integer,
    vertex_part integer,
    vertex_part_ring integer,
    vertex_part_index integer,
    distance numeric,
    angle numeric,
    xcoord double precision,
    ycoord double precision,
    zcoord double precision
);


ALTER TABLE public.coordinates OWNER TO postgres;

--
-- Name: coordinates_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.coordinates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.coordinates_id_seq OWNER TO postgres;

--
-- Name: coordinates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.coordinates_id_seq OWNED BY public.coordinates.id;


--
-- Name: coordinatesxregion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.coordinatesxregion (
    id_coordinates integer NOT NULL,
    id_region integer NOT NULL
);


ALTER TABLE public.coordinatesxregion OWNER TO postgres;

--
-- Name: region; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.region (
    id integer NOT NULL,
    des_region character varying(30) NOT NULL,
    id_middle_coord integer
);


ALTER TABLE public.region OWNER TO postgres;

--
-- Name: search_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.search_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.search_id_seq OWNER TO postgres;

--
-- Name: search; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.search (
    id integer DEFAULT nextval('public.search_id_seq'::regclass) NOT NULL,
    searchat timestamp without time zone,
    id_region integer NOT NULL,
    completed boolean DEFAULT false
);


ALTER TABLE public.search OWNER TO postgres;

--
-- Name: search_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.search_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.search_item_id_seq OWNER TO postgres;

--
-- Name: search_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.search_item (
    id integer DEFAULT nextval('public.search_item_id_seq'::regclass) NOT NULL,
    id_search integer NOT NULL,
    id_initial_coord integer NOT NULL,
    id_final_coord integer NOT NULL,
    distance double precision,
    processed boolean DEFAULT false
);


ALTER TABLE public.search_item OWNER TO postgres;

--
-- Name: search_itemxsegment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.search_itemxsegment (
    id_search_item integer NOT NULL,
    id_segment integer NOT NULL
);


ALTER TABLE public.search_itemxsegment OWNER TO postgres;

--
-- Name: segment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.segment (
    id integer NOT NULL,
    athletecount integer NOT NULL,
    createdat timestamp without time zone,
    distance double precision NOT NULL,
    effortcount integer NOT NULL,
    name character varying(150),
    polyline text,
    starcount integer NOT NULL,
    updatedat timestamp without time zone
);


ALTER TABLE public.segment OWNER TO postgres;

--
-- Name: segmenthistorical; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.segmenthistorical (
    id integer NOT NULL,
    athletecount integer NOT NULL,
    effortcount integer NOT NULL,
    starcount integer NOT NULL,
    updatedat timestamp without time zone,
    segment_id integer NOT NULL
);


ALTER TABLE public.segmenthistorical OWNER TO postgres;

--
-- Name: segmenthistorical_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.segmenthistorical_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.segmenthistorical_id_seq OWNER TO postgres;

--
-- Name: segmenthistorical_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.segmenthistorical_id_seq OWNED BY public.segmenthistorical.id;


--
-- Name: coordinates id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coordinates ALTER COLUMN id SET DEFAULT nextval('public.coordinates_id_seq'::regclass);


--
-- Name: segmenthistorical id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segmenthistorical ALTER COLUMN id SET DEFAULT nextval('public.segmenthistorical_id_seq'::regclass);


--
-- Name: coordinates coordinates_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coordinates
    ADD CONSTRAINT coordinates_pkey PRIMARY KEY (id);


--
-- Name: coordinatesxregion coordinatesxregion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coordinatesxregion
    ADD CONSTRAINT coordinatesxregion_pkey PRIMARY KEY (id_coordinates, id_region);


--
-- Name: region region_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.region
    ADD CONSTRAINT region_pkey PRIMARY KEY (id);


--
-- Name: search_item search_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_item
    ADD CONSTRAINT search_item_pkey PRIMARY KEY (id);


--
-- Name: search_itemxsegment search_itemxsegment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_itemxsegment
    ADD CONSTRAINT search_itemxsegment_pkey PRIMARY KEY (id_search_item, id_segment);


--
-- Name: search search_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search
    ADD CONSTRAINT search_pkey PRIMARY KEY (id);


--
-- Name: segment segment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segment
    ADD CONSTRAINT segment_pkey PRIMARY KEY (id);


--
-- Name: segmenthistorical segmenthistorical_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segmenthistorical
    ADD CONSTRAINT segmenthistorical_pkey PRIMARY KEY (id);


--
-- Name: segmenthistorical fk7nr4yns9ogx8wr10tl1vxvpnl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.segmenthistorical
    ADD CONSTRAINT fk7nr4yns9ogx8wr10tl1vxvpnl FOREIGN KEY (segment_id) REFERENCES public.segment(id);


--
-- Name: region fk_middle_coord_region; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.region
    ADD CONSTRAINT fk_middle_coord_region FOREIGN KEY (id_middle_coord) REFERENCES public.coordinates(id);


--
-- Name: search fk_search_reg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search
    ADD CONSTRAINT fk_search_reg FOREIGN KEY (id_region) REFERENCES public.region(id);


--
-- Name: search_item search_item_id_final_coord_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_item
    ADD CONSTRAINT search_item_id_final_coord_fkey FOREIGN KEY (id_final_coord) REFERENCES public.coordinates(id);


--
-- Name: search_item search_item_id_initial_coord_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_item
    ADD CONSTRAINT search_item_id_initial_coord_fkey FOREIGN KEY (id_initial_coord) REFERENCES public.coordinates(id);


--
-- Name: search_item search_item_id_search_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_item
    ADD CONSTRAINT search_item_id_search_fkey FOREIGN KEY (id_search) REFERENCES public.search(id);


--
-- Name: search_itemxsegment search_itemxsegment_id_search_item_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_itemxsegment
    ADD CONSTRAINT search_itemxsegment_id_search_item_fkey FOREIGN KEY (id_search_item) REFERENCES public.search_item(id);


--
-- Name: search_itemxsegment search_itemxsegment_id_segment_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.search_itemxsegment
    ADD CONSTRAINT search_itemxsegment_id_segment_fkey FOREIGN KEY (id_segment) REFERENCES public.segment(id);


--
-- PostgreSQL database dump complete
--

