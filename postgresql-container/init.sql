-- public.hotel_search_details definition

CREATE TABLE IF NOT EXISTS public.hotel_search_details
(
  id        bigserial NOT NULL,
  check_in  date NULL,
  check_out date NULL,
  hotel_id  varchar(255) NULL,
  CONSTRAINT hotel_search_details_pkey PRIMARY KEY (id)
);

-- public.hotel_search definition

CREATE TABLE IF NOT EXISTS public.hotel_search
(
  id                      bigserial NOT NULL,
  count                   int8 NULL,
  last_search_at          timestamp(6) NULL,
  search_id               varchar(255) NULL,
  hotel_search_details_id int8 NULL,
  CONSTRAINT hotel_search_pkey PRIMARY KEY (id),
  CONSTRAINT uk_3157p1vs2ls2jcsy8bphel1ob UNIQUE (search_id),
  CONSTRAINT uk_hoia5kvs3a9y5fy2bdt9f36jt UNIQUE (hotel_search_details_id),
  CONSTRAINT fkrlv39et5wd2uuo8hvnfn8l9op FOREIGN KEY (hotel_search_details_id) REFERENCES public.hotel_search_details (id)
);

-- public.ages definition

CREATE TABLE IF NOT EXISTS public.ages
(
  id   int8 NOT NULL,
  ages int4 NULL,
  CONSTRAINT fkgcf7pcqoo15n30y5g0mxtc5hl FOREIGN KEY (id) REFERENCES public.hotel_search_details (id)
);