ALTER TABLE public.category_timings
ADD COLUMN active BOOLEAN DEFAULT TRUE;

ALTER TABLE public.category_timings
ADD COLUMN name VARCHAR(255);

ALTER TABLE public.category_timings
ADD COLUMN description VARCHAR(255);

