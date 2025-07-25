-- V2__insert_marcas_modelos.sql

INSERT INTO marca (id, nome) VALUES
                                 ('ca43ec74-5bb0-4288-ab11-5df094ca4dc4', 'FIAT'),
                                 ('c0225595-e293-477b-8758-384872470f00', 'FORD'),
                                 ('e66e8451-a442-4344-bbd9-17f249d9eea4', 'CHEVROLET');

INSERT INTO modelo (id, nome, marca_id) VALUES
                                            ('5bc16064-d3ee-4aed-a264-a914233d0c4f', '147 C/ CL', 'ca43ec74-5bb0-4288-ab11-5df094ca4dc4'),
                                            ('e16e9ed4-43c6-4882-9f2f-12ca5aad6e7e', 'ARGO 1.0 6V Flex.', 'ca43ec74-5bb0-4288-ab11-5df094ca4dc4'),
                                            ('6533c337-f745-437c-8adf-a20895275cbf', 'Doblo ESSENCE 1.8 Flex 16V 5p', 'ca43ec74-5bb0-4288-ab11-5df094ca4dc4'),

                                            ('c08f77df-c6e0-4e73-a378-42bb83361266', 'Belina GL 1.8 / 1.6', 'c0225595-e293-477b-8758-384872470f00'),
                                            ('deaf80e7-600c-4a35-af52-1fc7f1e967a8', 'EcoSport XL 1.6/ 1.6 Flex 8V 5p', 'c0225595-e293-477b-8758-384872470f00'),
                                            ('b1c9a613-29ee-4171-a5ff-e7d98a0fdaac', 'Fiesta SEL Style 1.6 16V Flex Mec. 5p', 'c0225595-e293-477b-8758-384872470f00'),

                                            ('828bd4bf-ea80-4449-bf8f-154cda91d864', 'Astra Eleg. 2.0 MPFI FlexPower 8V 5p Aut', 'e66e8451-a442-4344-bbd9-17f249d9eea4'),
                                            ('cc0b4033-9624-400d-b45d-84cceb7e0441', 'Celta Life 1.0 MPFI VHC 8V 3p', 'e66e8451-a442-4344-bbd9-17f249d9eea4'),
                                            ('7a9e2990-b356-40e6-b0b5-c26d38e3f5bb', 'Meriva Joy 1.8 MPFI 8V FlexPower', 'e66e8451-a442-4344-bbd9-17f249d9eea4');
