import { useCallback, useState } from 'react';

import { useTranslation }  from "react-i18next";

import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Divider,
  TextField,
  Unstable_Grid2 as Grid
} from '@mui/material';

const states = [
  {
    value: 1,
    label: 'ADMIN'
  },
  {
    value: 2,
    label: 'CHEF'
  }
];

export const AccountProfileDetails = () => {
  const { i18n, t } = useTranslation();
  
  const [values, setValues] = useState({
    fullname: 'Anika',
    password: 'Visser',
    email: 'demo@devias.io',
    role: ''
  });

  const handleChange = useCallback(
    (event) => {
      setValues((prevState) => ({
        ...prevState,
        [event.target.name]: event.target.value
      }));
    },
    []
  );

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
    },
    []
  );

  return (
    <form
      autoComplete="off"
      noValidate
      onSubmit={handleSubmit}
    >
      <Card>
        <CardHeader
          subheader="The information can be edited"
          title="Profile"
        />
        <CardContent sx={{ pt: 0 }}>
          <Box sx={{ m: -1.5 }}>
            <Grid
              container
              spacing={3}
            >
              <Grid
                xs={12}
                md={6}
              >
                <TextField
                  fullWidth
                  label= {t("title")}
                  name="fullname"
                  onChange={handleChange}
                  required
                  value={values.firstName}
                />
              </Grid>
              <Grid
                xs={12}
                md={6}
              >
                <TextField
                  fullWidth
                  label={t("password")}
                  name="password"
                  onChange={handleChange}
                  type='password'
                  required
                  value={values.lastName}
                />
              </Grid>
            
              <Grid
                xs={12}
                md={6}
              >
                <TextField
                  fullWidth
                  label={t("address-email")}
                  name="email"
                  onChange={handleChange}
                  required
                  value={values.country}
                />
              </Grid>
              <Grid
                xs={12}
                md={6}
              >
                <TextField
                  fullWidth
                  label= {t("user-select-rolename")}
                  name="role"
                  onChange={handleChange}
                  required
                  select
                  SelectProps={{ native: true }}
                  value={values.state}
                >
                  {states.map((option) => (
                    <option
                      key={option.value}
                      value={option.value}
                    >
                      {option.label}
                    </option>
                  ))}
                </TextField>
              </Grid>
            </Grid>
          </Box>
        </CardContent>
        <Divider />
        <CardActions sx={{ justifyContent: 'flex-end' }}>
          <Button variant="contained">
          {t("save")}
          </Button>
        </CardActions>
      </Card>
    </form>
  );
};
